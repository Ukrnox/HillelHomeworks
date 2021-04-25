package org.hillel.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hillel.entity.Group;

public class GroupDao implements Dao<Group>
{
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void create(Group entity)
    {
        if(entity != null)
        {
            try (Session session = sessionFactory.openSession())
            {
                session.beginTransaction();

                session.save(entity);

                session.getTransaction().commit();
            }
        }
    }

    @Override
    public Group read(long groupId)
    {
        Group group;
        try (Session session = sessionFactory.openSession())
        {
            group = session.get(Group.class, groupId);
            if (group != null)
            {
                Hibernate.initialize(group.getTopics());
                Hibernate.initialize(group.getUsers());
            }
        }
        return group;
    }

    @Override
    public void update(Group entity)
    {
        if(entity != null)
        {
            try (Session session = sessionFactory.openSession())
            {
                session.beginTransaction();

                session.update(entity);

                session.getTransaction().commit();
            }
        }
    }

    @Override
    public boolean delete(long groupId)
    {
        Group group;
        boolean result = false;
        try (Session session = sessionFactory.openSession())
        {
            group = session.get(Group.class, groupId);
            if (group != null)
            {
                session.beginTransaction();

                session.delete(group);

                session.getTransaction().commit();
                result = true;
            }
        }
        return result;
    }
}
