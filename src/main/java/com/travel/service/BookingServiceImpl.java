package com.travel.service;

import com.travel.model.FlightBooking;
import com.travel.model.HotelBooking;
import com.travel.model.Flight;
import com.travel.model.Hotel;
import com.travel.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("null")
    @Override
    public FlightBooking bookFlight(int userId, int flightId, int passengers) {
        Flight flight;
        try {
            flight = jdbcTemplate.queryForObject(
                    "SELECT * FROM flights WHERE id=?",
                    (rs, rowNum) -> mapFlight(rs),
                    flightId
            );
        } catch (EmptyResultDataAccessException e) {
            System.out.println("❌ Flight not found.");
            return null;
        }

        if (flight.getAvailableSeats() < passengers) {
            System.out.println("❌ Not enough seats.");
            return null;
        }

        // Update seats
        jdbcTemplate.update(
                "UPDATE flights SET available_seats=? WHERE id=?",
                flight.getAvailableSeats() - passengers,
                flightId
        );

        double totalPrice = flight.getPrice() * passengers;

        // Insert booking
        jdbcTemplate.update(
                "INSERT INTO flight_bookings (user_id, flight_id, passengers, status, total_price) VALUES (?,?,?,?,?)",
                userId, flightId, passengers, "CONFIRMED", totalPrice
        );

        // Return latest booking
        return jdbcTemplate.queryForObject(
                "SELECT * FROM flight_bookings WHERE user_id=? AND flight_id=? ORDER BY id DESC LIMIT 1",
                (rs, rowNum) -> mapFlightBooking(rs),
                userId, flightId
        );
    }

    @SuppressWarnings("null")
    @Override
    public HotelBooking bookHotel(int userId, int hotelId, int rooms, LocalDate checkIn, LocalDate checkOut) {
        Hotel hotel;
        try {
            hotel = jdbcTemplate.queryForObject(
                    "SELECT * FROM hotels WHERE id=?",
                    (rs, rowNum) -> mapHotel(rs),
                    hotelId
            );
        } catch (EmptyResultDataAccessException e) {
            System.out.println("❌ Hotel not found.");
            return null;
        }

        if (hotel.getAvailableRooms() < rooms) {
            System.out.println("❌ Not enough rooms.");
            return null;
        }

        long nights = checkOut.toEpochDay() - checkIn.toEpochDay();
        double totalPrice = hotel.getPricePerNight() * rooms * nights;

        // Update rooms
        jdbcTemplate.update(
                "UPDATE hotels SET available_rooms=? WHERE id=?",
                hotel.getAvailableRooms() - rooms,
                hotelId
        );

        // Insert booking
        jdbcTemplate.update(
                "INSERT INTO hotel_bookings (user_id, hotel_id, rooms, check_in, check_out, status, total_price) VALUES (?,?,?,?,?,?,?)",
                userId, hotelId, rooms, checkIn, checkOut, "CONFIRMED", totalPrice
        );

        return jdbcTemplate.queryForObject(
                "SELECT * FROM hotel_bookings WHERE user_id=? AND hotel_id=? ORDER BY id DESC LIMIT 1",
                (rs, rowNum) -> mapHotelBooking(rs),
                userId, hotelId
        );
    }

    @Override
    public List<FlightBooking> viewFlightBookings(int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM flight_bookings WHERE user_id=?",
                (rs, rowNum) -> mapFlightBooking(rs),
                userId
        );
    }

    @Override
    public List<HotelBooking> viewHotelBookings(int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM hotel_bookings WHERE user_id=?",
                (rs, rowNum) -> mapHotelBooking(rs),
                userId
        );
    }

    @Override
    public boolean cancelFlightBooking(int bookingId) {
        return jdbcTemplate.update("DELETE FROM flight_bookings WHERE id=?", bookingId) > 0;
    }

    @Override
    public boolean cancelHotelBooking(int bookingId) {
        return jdbcTemplate.update("DELETE FROM hotel_bookings WHERE id=?", bookingId) > 0;
    }

    // ----------------- Mapping Helpers -----------------
    private Flight mapFlight(ResultSet rs) throws SQLException {
        Flight f = new Flight();
        f.setId(rs.getInt("id"));
        f.setFlightNumber(rs.getString("flight_number"));
        f.setAirline(rs.getString("airline"));
        f.setOrigin(rs.getString("origin"));
        f.setDestination(rs.getString("destination"));
        f.setPrice(rs.getDouble("price"));
        f.setAvailableSeats(rs.getInt("available_seats"));
        return f;
    }

    private Hotel mapHotel(ResultSet rs) throws SQLException {
        Hotel h = new Hotel();
        h.setId(rs.getInt("id"));
        h.setName(rs.getString("name"));
        h.setLocation(rs.getString("location"));
        h.setPricePerNight(rs.getDouble("price_per_night"));
        h.setAvailableRooms(rs.getInt("available_rooms"));
        h.setAmenities(rs.getString("amenities"));
        return h;
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setUsername(rs.getString("username")); // corrected here
        u.setEmail(rs.getString("email"));
        return u;
    }

    private FlightBooking mapFlightBooking(ResultSet rs) throws SQLException {
        FlightBooking fb = new FlightBooking();
        fb.setId(rs.getInt("id"));
        fb.setPassengers(rs.getInt("passengers"));
        fb.setStatus(rs.getString("status"));
        fb.setTotalPrice(rs.getDouble("total_price"));

        // Set Flight
        int flightId = rs.getInt("flight_id");
        fb.setFlight(jdbcTemplate.queryForObject(
                "SELECT * FROM flights WHERE id=?",
                (r, rowNum) -> mapFlight(r),
                flightId
        ));

        // Set User
        int userId = rs.getInt("user_id");
        fb.setUser(jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id=?",
                (r, rowNum) -> mapUser(r),
                userId
        ));

        return fb;
    }

    private HotelBooking mapHotelBooking(ResultSet rs) throws SQLException {
        HotelBooking hb = new HotelBooking();
        hb.setId(rs.getInt("id"));
        hb.setRooms(rs.getInt("rooms"));
        hb.setStatus(rs.getString("status"));
        hb.setTotalPrice(rs.getDouble("total_price"));
        hb.setCheckInDate(rs.getDate("check_in").toLocalDate());
        hb.setCheckOutDate(rs.getDate("check_out").toLocalDate());

        // Set Hotel
        int hotelId = rs.getInt("hotel_id");
        hb.setHotel(jdbcTemplate.queryForObject(
                "SELECT * FROM hotels WHERE id=?",
                (r, rowNum) -> mapHotel(r),
                hotelId
        ));

        // Set User
        int userId = rs.getInt("user_id");
        hb.setUser(jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id=?",
                (r, rowNum) -> mapUser(r),
                userId
        ));

        return hb;
    }
}
