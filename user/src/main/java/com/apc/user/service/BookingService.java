package com.apc.user.service;

import com.apc.common.dao.FlightBookingDAO;
import com.apc.common.dao.FlightDAO;
import com.apc.common.dao.HotelBookingDAO;
import com.apc.common.dao.HotelDAO;
import com.apc.common.model.*;
import com.apc.common.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Service
public class BookingService {

    private final FlightDAO flightDAO = new FlightDAO();
    private final HotelDAO hotelDAO = new HotelDAO();
    private final FlightBookingDAO flightBookingDAO = new FlightBookingDAO();
    private final HotelBookingDAO hotelBookingDAO = new HotelBookingDAO();

    // Console book flight (interactive)
    public void bookFlightConsole(Scanner sc, User user) {
        List<Flight> flights = flightDAO.findAll();
        if (flights.isEmpty()) {
            System.out.println("No flights available.");
            return;
        }
        flights.forEach(f -> System.out
                .println(f.getId() + " - " + f.getFlightNumber() + " " + f.getOrigin() + "->" + f.getDestination()
                        + " seats:" + f.getAvailableSeats() + " price:" + f.getPrice()));
        System.out.print("Enter flight id to book: ");
        int fid = Integer.parseInt(sc.nextLine());
        Flight flight = flightDAO.findById(fid);
        if (flight == null) {
            System.out.println("Flight not found.");
            return;
        }
        System.out.print("Passengers: ");
        int passengers = Integer.parseInt(sc.nextLine());
        if (passengers <= 0 || passengers > flight.getAvailableSeats()) {
            System.out.println("Invalid passenger count or not enough seats.");
            return;
        }
        // update flight seats
        flight.setAvailableSeats(flight.getAvailableSeats() - passengers);
        flightDAO.update(flight);

        FlightBooking fb = new FlightBooking();
        fb.setUser(user);
        fb.setFlight(flight);
        fb.setPassengers(passengers);
        FlightBooking saved = flightBookingDAO.save(fb);
        System.out.println(saved != null ? "Flight booked id=" + saved.getId() : "Booking failed.");
    }

    // Console book hotel (interactive)
    public void bookHotelConsole(Scanner sc, User user) {
        List<Hotel> hotels = hotelDAO.findAll();
        if (hotels.isEmpty()) {
            System.out.println("No hotels available.");
            return;
        }
        hotels.forEach(h -> System.out.println(h.getId() + " - " + h.getName() + " @" + h.getLocation() + " rooms:"
                + h.getAvailableRooms() + " price:" + h.getPricePerNight()));
        System.out.print("Enter hotel id to book: ");
        int hid = Integer.parseInt(sc.nextLine());
        Hotel hotel = hotelDAO.findById(hid);
        if (hotel == null) {
            System.out.println("Hotel not found.");
            return;
        }
        System.out.print("Rooms to book: ");
        int rooms = Integer.parseInt(sc.nextLine());
        System.out.print("Check-in date (yyyy-MM-dd): ");
        LocalDate checkIn = LocalDate.parse(sc.nextLine());
        System.out.print("Check-out date (yyyy-MM-dd): ");
        LocalDate checkOut = LocalDate.parse(sc.nextLine());
        if (!checkOut.isAfter(checkIn)) {
            System.out.println("Check-out must be after check-in.");
            return;
        }
        if (rooms <= 0 || rooms > hotel.getAvailableRooms()) {
            System.out.println("Invalid rooms or not enough rooms.");
            return;
        }

        hotel.setAvailableRooms(hotel.getAvailableRooms() - rooms);
        hotelDAO.update(hotel);

        HotelBooking hb = new HotelBooking();
        hb.setUser(user);
        hb.setHotel(hotel);
        hb.setRooms(rooms);
        hb.setCheckInDate(checkIn);
        hb.setCheckOutDate(checkOut);
        HotelBooking saved = hotelBookingDAO.save(hb);
        System.out.println(saved != null ? "Hotel booked id=" + saved.getId() : "Booking failed.");
    }

    public List<FlightBooking> listFlightBookings() {
        return flightBookingDAO.findAll();
    }

    public List<HotelBooking> listHotelBookings() {
        return hotelBookingDAO.findAll();
    }

    // For controllers you might add cancel methods (already present in earlier
    // code)
    public boolean cancelFlightBooking(int id) {
        return flightBookingDAO.delete(id);
    }

    public boolean cancelHotelBooking(int id) {
        return hotelBookingDAO.delete(id);
    }

    public FlightBooking bookFlight(User user, int flightId, int passengers) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Flight flight = session.get(Flight.class, flightId);
            if (flight == null || flight.getAvailableSeats() < passengers) {
                throw new RuntimeException("Flight not available or insufficient seats");
            }
            flight.setAvailableSeats(flight.getAvailableSeats() - passengers);
            FlightBooking booking = new FlightBooking();
            booking.setUser(user);
            booking.setFlight(flight);
            booking.setPassengers(passengers);
            session.persist(booking);
            tx.commit();
            return booking;
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            throw e;
        }
    }

    public HotelBooking bookHotel(User user, int hotelId, int rooms, LocalDate checkIn, LocalDate checkOut) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Hotel hotel = session.get(Hotel.class, hotelId);
            if (hotel == null || hotel.getAvailableRooms() < rooms) {
                throw new RuntimeException("Hotel not available or insufficient rooms");
            }
            hotel.setAvailableRooms(hotel.getAvailableRooms() - rooms);
            HotelBooking booking = new HotelBooking();
            booking.setUser(user);
            booking.setHotel(hotel);
            booking.setRooms(rooms);
            booking.setCheckInDate(checkIn);
            booking.setCheckOutDate(checkOut);
            session.persist(booking);
            tx.commit();
            return booking;
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            throw e;
        }
    }
}
