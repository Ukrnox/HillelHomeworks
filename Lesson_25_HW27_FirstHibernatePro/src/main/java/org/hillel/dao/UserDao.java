package org.hillel.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hillel.entity.Post;
import org.hillel.entity.User;
import org.hillel.entity.Votes;

import javax.persistence.NoResultException;
import java.util.List;

public class UserDao implements Dao<User>
{


    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public boolean createOrUpdateVote(long userId, long postId, boolean positive)
    {
        boolean result = false;
        try (Session session = sessionFactory.openSession())
        {
            User readUser = session.get(User.class, userId);
            Post readPost = session.get(Post.class, postId);
            if (readUser != null && readPost != null) {

                long upVote = positive ? 1 : 0;

                long downVote = !positive ? 1 : 0;

                try
                {
                    Votes readVote = (Votes) session.createQuery(
                            "from Votes where post_id = " + postId + " and user_id = " + userId).getSingleResult();

                    readVote.setUpVotes(upVote);

                    readVote.setDownVotes(downVote);

                    session.beginTransaction();

                    session.update(readVote);

                    session.getTransaction().commit();

                }
                catch (NoResultException e)
                {
                    Votes vote = new Votes();

                    vote.setPost(readPost);

                    vote.setAuthor(readUser);

                    vote.setDownVotes(downVote);

                    vote.setUpVotes(upVote);

                    session.beginTransaction();

                    session.save(vote);

                    session.getTransaction().commit();
                }
                result = true;
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<User> loadAll()
    {
        List<User> users;
        try (Session session = sessionFactory.openSession())
        {
            users = session.createQuery("from User").list();
        }
        return users;
    }

    @Override
    public void create(User entity)
    {
        try (Session session = sessionFactory.openSession())
        {
            session.beginTransaction();

            session.save(entity);

            session.getTransaction().commit();
        }
    }

    @Override
    public User read(long userId)
    {
        User user;
        try (Session session = sessionFactory.openSession())
        {
            user = session.get(User.class, userId);
            if (user != null)
            {
                Hibernate.initialize(user.getGroups());
                Hibernate.initialize(user.getTopics());
                Hibernate.initialize(user.getPosts());
                Hibernate.initialize(user.getVotes());
            }
        }
        return user;
    }

    @Override
    public void update(User entity)
    {
        if(entity != null)
        {
            Transaction transaction;
            try (Session session = sessionFactory.openSession())
            {
                transaction = session.beginTransaction();

                session.update(entity);

                transaction.commit();

            }
        }
    }

    @Override
    public boolean delete(long userId)
    {
        User user;
        boolean result = false;
        try (Session session = sessionFactory.openSession())
        {
            user = session.get(User.class, userId);
            if (user != null)
            {
                session.beginTransaction();

                session.delete(user);

                session.getTransaction().commit();
                result = true;
            }
        }
        return result;
    }
}
