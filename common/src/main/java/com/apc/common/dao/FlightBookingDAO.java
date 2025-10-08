package com.apc.common.dao;

import com.apc.common.model.FlightBooking;
import com.apc.common.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FlightBookingDAO {
    public FlightBooking save(FlightBooking fb) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.persist(fb);
            tx.commit();
            return fb;
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
            return null;
        }
    }

    public boolean delete(int id) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            FlightBooking fb = s.get(FlightBooking.class, id);
            if (fb == null) return false;
            tx = s.beginTransaction();
            s.remove(fb);
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    public FlightBooking findById(int id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(FlightBooking.class, id);
        }
    }

    public List<FlightBooking> findAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("FROM FlightBooking", FlightBooking.class).list();
        }
    }
}
