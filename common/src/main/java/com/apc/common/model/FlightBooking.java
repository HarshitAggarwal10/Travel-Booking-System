package com.apc.common.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.io.Serializable;

@Entity
@Table(name = "flight_bookings")
public class FlightBooking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional=false)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(optional=false)
    @JoinColumn(name="flight_id")
    private Flight flight;

    @Column(nullable=false)
    private int passengers;

    @Column(nullable=false)
    private LocalDateTime bookingDate = LocalDateTime.now();

    public FlightBooking() {}

    // getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Flight getFlight() { return flight; }
    public void setFlight(Flight flight) { this.flight = flight; }

    public int getPassengers() { return passengers; }
    public void setPassengers(int passengers) { this.passengers = passengers; }

    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }

    @Override
    public String toString() {
        return "FlightBooking{" +
                "id=" + id +
                ", user=" + user.getUsername() +
                ", flight=" + flight.getFlightNumber() +
                ", passengers=" + passengers +
                ", bookingDate=" + bookingDate +
                '}';
    }
}
