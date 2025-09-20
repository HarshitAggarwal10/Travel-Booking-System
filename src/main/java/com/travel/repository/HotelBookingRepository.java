package com.travel.repository;

import com.travel.model.HotelBooking;
import java.util.ArrayList;
import java.util.List;

public class HotelBookingRepository {
    private final List<HotelBooking> bookings = new ArrayList<>();
    private int idCounter = 1;

    public HotelBooking save(HotelBooking booking) {
        if (booking.getId() == 0) {
            booking.setId(idCounter++);
            bookings.add(booking);
        } else {
            bookings.replaceAll(b -> b.getId() == booking.getId() ? booking : b);
        }
        return booking;
    }

    public HotelBooking findById(int id) {
        return bookings.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    public List<HotelBooking> findAll() {
        return new ArrayList<>(bookings);
    }

    public List<HotelBooking> findByUserId(int userId) {
        return bookings.stream().filter(b -> b.getId() == userId).toList();
    }

    public boolean delete(long bookingId) {
        return bookings.removeIf(b -> b.getId() == bookingId);
    }

    public boolean existsById(int bookingId) {
        throw new UnsupportedOperationException("Unimplemented method 'existsById'");
    }

}
