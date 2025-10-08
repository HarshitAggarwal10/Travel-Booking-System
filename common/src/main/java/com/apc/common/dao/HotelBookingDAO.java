package com.apc.common.dao;

import com.apc.common.model.HotelBooking;
import com.apc.common.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HotelBookingDAO {
    public HotelBooking save(HotelBooking hb) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.persist(hb);
            tx.commit();
            return hb;
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
            return null;
        }
    }

    public boolean delete(int id) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            HotelBooking hb = s.get(HotelBooking.class, id);
            if (hb == null) return false;
            tx = s.beginTransaction();
            s.remove(hb);
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    public HotelBooking findById(int id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(HotelBooking.class, id);
        }
    }

    public List<HotelBooking> findAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("FROM HotelBooking", HotelBooking.class).list();
        }
    }
}
