package org.hillel.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hillel.entity.Post;
import org.hillel.entity.Votes;

import java.util.HashMap;

public class VotesDao implements Dao<Votes>
{

    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public HashMap<String, Long> calculateVotes(long postId)
    {
        HashMap<String, Long> result = new HashMap<>();
        result.put("UpVotes", 0L);
        result.put("DownVotes", 0L);

        try (Session session = sessionFactory.openSession())
        {
            Post post = session.get(Post.class, postId);
            if (post != null)
            {
                long upVotes = 0;
                long downVotes = 0;
                for (Votes v : post.getVotes())
                {
                    upVotes += v.getUpVotes();
                    downVotes += v.getDownVotes();
                }
                result.put("UpVotes", upVotes);
                result.put("DownVotes", downVotes);
            }
        }
        return result;
    }

    @Override
    public void create(Votes entity)
    {
        try (Session session = sessionFactory.openSession())
        {
            session.beginTransaction();

            session.save(entity);

            session.getTransaction().commit();
        }
    }

    @Override
    public Votes read(long voteId)
    {
        Votes topic;
        try (Session session = sessionFactory.openSession())
        {
            topic = session.get(Votes.class, voteId);
        }
        return topic;
    }

    @Override
    public void update(Votes entity)
    {
        try (Session session = sessionFactory.openSession())
        {
            session.beginTransaction();

            session.update(entity);

            session.getTransaction().commit();
        }
    }

    @Override
    public boolean delete(long voteId)
    {
        Votes topic;
        boolean result = false;
        try (Session session = sessionFactory.openSession())
        {
            topic = session.get(Votes.class, voteId);
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
