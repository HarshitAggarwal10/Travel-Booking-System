package com.travel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "flight_bookings")
public class FlightBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @Column(nullable = false)
    private int passengers;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private double totalPrice;

    public FlightBooking() {}

    public FlightBooking(User user, Flight flight, int passengers, String status, double totalPrice) {
        this.user = user;
        this.flight = flight;
        this.passengers = passengers;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Flight getFlight() { return flight; }
    public void setFlight(Flight flight) { this.flight = flight; }

    public int getPassengers() { return passengers; }
    public void setPassengers(int passengers) { this.passengers = passengers; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    @Override
    public String toString() {
        return "FlightBooking{" +
                "ID=" + id +
                ", User=" + (user != null ? user.getUsername() : "null") +
                ", Flight=" + (flight != null ? flight.getFlightNumber() : "null") +
                ", Passengers=" + passengers +
                ", Status='" + status + '\'' +
                ", TotalPrice=" + totalPrice +
                '}';
    }
}
