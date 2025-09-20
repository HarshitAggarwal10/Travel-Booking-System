package com.travel.service;

import com.travel.model.FlightBooking;
import com.travel.model.HotelBooking;
import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    FlightBooking bookFlight(int userId, int flightId, int passengers);
    HotelBooking bookHotel(int userId, int hotelId, int rooms, LocalDate checkIn, LocalDate checkOut);
    List<FlightBooking> viewFlightBookings(int userId);
    List<HotelBooking> viewHotelBookings(int userId);
    boolean cancelFlightBooking(int bookingId);
    boolean cancelHotelBooking(int bookingId);
}
