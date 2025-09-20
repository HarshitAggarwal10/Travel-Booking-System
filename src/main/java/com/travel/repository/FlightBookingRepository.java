package com.travel.repository;

import com.travel.model.FlightBooking;
import com.travel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightBookingRepository extends JpaRepository<FlightBooking, Integer> {

    // Find bookings for a specific user
    List<FlightBooking> findByUser(User user);
}
