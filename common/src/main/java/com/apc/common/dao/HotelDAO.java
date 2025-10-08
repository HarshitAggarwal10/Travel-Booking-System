package com.apc.common.dao;

import com.apc.common.model.Hotel;
import com.apc.common.util.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HotelDAO {
    public Hotel save(Hotel h) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.persist(h);
            tx.commit();
            return h;
        } catch (Exception ex) {
            if (tx != null)
                tx.rollback();
            ex.printStackTrace();
            return null;
        }
    }

    public Hotel update(Hotel h) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.merge(h);
            tx.commit();
            return h;
        } catch (Exception ex) {
            if (tx != null)
                tx.rollback();
            ex.printStackTrace();
            return null;
        }
    }

    public boolean delete(int id) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Hotel h = s.get(Hotel.class, id);
            if (h == null)
                return false;
            tx = s.beginTransaction();
            s.remove(h);
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx != null)
                tx.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    public Hotel findById(int id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Hotel.class, id);
        }
    }

    public List<Hotel> findAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("FROM Hotel", Hotel.class).list();
        }
    }

    public List<Hotel> searchByLocation(String location) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("FROM Hotel WHERE location LIKE :loc", Hotel.class)
                    .setParameter("loc", "%" + location + "%").list();
        }
    }

    @Transactional
    public boolean deleteById(int id) {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        Hotel h = em.find(Hotel.class, id);
        if (h != null) {
            em.getTransaction().begin();
            em.remove(h);
            em.getTransaction().commit();
            em.close();
            return true;
        }
        em.close();
        return false;
    }
}
