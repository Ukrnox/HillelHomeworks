package org.hillel.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hillel.entity.Post;

public class PostDao implements Dao<Post>
{
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void create(Post entity)
    {
        try (Session session = sessionFactory.openSession())
        {
            session.beginTransaction();

            session.save(entity);

            session.getTransaction().commit();
        }
    }

    @Override
    public Post read(long postId)
    {
        Post topic;
        try (Session session = sessionFactory.openSession())
        {
            topic = session.get(Post.class, postId);
            Hibernate.initialize(topic.getVotes());
            Hibernate.initialize(topic.getAuthor());

        }
        return topic;
    }

    @Override
    public void update(Post entity)
    {
        try (Session session = sessionFactory.openSession())
        {
            session.beginTransaction();

            session.update(entity);

            session.getTransaction().commit();
        }
    }

    @Override
    public boolean delete(long postId)
    {
        Post topic;
        boolean result = false;
        try (Session session = sessionFactory.openSession())
        {
            topic = session.get(Post.class, postId);
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
