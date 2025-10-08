package com.apc.common.dao;

import com.apc.common.model.User;
import com.apc.common.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAO {

    // Save or update a user
    public User save(User u) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(u); // saveOrUpdate ensures persistence
            tx.commit();             // commit is essential
            return u;
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
            return null;
        }
    }

    // Get all users
    public List<User> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).list();
        }
    }

    // Find user by ID
    public User findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        }
    }

    // Find user by username
    public User findByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User WHERE username = :u", User.class)
                    .setParameter("u", username)
                    .uniqueResult();
        }
    }

    // Delete user by ID
    public boolean deleteById(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User u = session.get(User.class, id);
            if (u == null) return false;
            tx = session.beginTransaction();
            session.delete(u);  // remove() can work, delete() is safer here
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
            return false;
        }
    }
}
