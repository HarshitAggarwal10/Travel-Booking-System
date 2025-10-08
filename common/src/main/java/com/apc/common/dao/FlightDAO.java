package com.apc.common.dao;

import com.apc.common.model.Flight;
import com.apc.common.util.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FlightDAO {
    public Flight save(Flight f) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.persist(f);
            tx.commit();
            return f;
        } catch (Exception ex) {
            if (tx != null)
                tx.rollback();
            ex.printStackTrace();
            return null;
        }
    }

    public Flight update(Flight f) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.merge(f);
            tx.commit();
            return f;
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
            Flight f = s.get(Flight.class, id);
            if (f == null)
                return false;
            tx = s.beginTransaction();
            s.remove(f);
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx != null)
                tx.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    public Flight findById(int id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Flight.class, id);
        }
    }

    public List<Flight> findAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("FROM Flight", Flight.class).list();
        }
    }

    public List<Flight> search(String origin, String destination) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("FROM Flight WHERE origin = :o AND destination = :d", Flight.class)
                    .setParameter("o", origin).setParameter("d", destination).list();
        }
    }

    @Transactional
    public boolean deleteById(int id) {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        Flight f = em.find(Flight.class, id);
        if (f != null) {
            em.getTransaction().begin();
            em.remove(f);
            em.getTransaction().commit();
            em.close();
            return true;
        }
        em.close();
        return false;
    }
}
