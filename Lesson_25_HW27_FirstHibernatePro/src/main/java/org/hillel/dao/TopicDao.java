package org.hillel.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hillel.entity.Topic;

public class TopicDao implements Dao<Topic>
{
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void create(Topic entity)
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
    public Topic read(long topicId)
    {
        Topic topic;
        try (Session session = sessionFactory.openSession())
        {
            topic = session.get(Topic.class, topicId);
            if(topic != null)
            {
                Hibernate.initialize(topic.getPosts());
                Hibernate.initialize(topic.getGroup());
                Hibernate.initialize(topic.getAuthor());
            }
        }
        return topic;
    }

    @Override
    public void update(Topic entity)
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
    public boolean delete(long topicId)
    {
        Topic topic;
        boolean result = false;
        try (Session session = sessionFactory.openSession())
        {
            topic = session.get(Topic.class, topicId);
            if (topic != null)
            {
                session.beginTransaction();

                session.delete(topic);

                session.getTransaction().commit();
                result = true;
            }
        }
        return result;
    }
}
