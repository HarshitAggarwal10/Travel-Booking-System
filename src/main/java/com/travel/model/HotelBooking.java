package com.travel.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "hotel_bookings")
public class HotelBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Column(nullable = false)
    private int rooms;

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private double totalPrice;

    public HotelBooking() {}

    public HotelBooking(User user, Hotel hotel, int rooms, LocalDate checkInDate, LocalDate checkOutDate, String status, double totalPrice) {
        this.user = user;
        this.hotel = hotel;
        this.rooms = rooms;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Hotel getHotel() { return hotel; }
    public void setHotel(Hotel hotel) { this.hotel = hotel; }

    public int getRooms() { return rooms; }
    public void setRooms(int rooms) { this.rooms = rooms; }

    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }

    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    @Override
    public String toString() {
        return "HotelBooking{" +
                "ID=" + id +
                ", User=" + (user != null ? user.getUsername() : "null") +
                ", Hotel=" + (hotel != null ? hotel.getName() : "null") +
                ", Rooms=" + rooms +
                ", CheckIn=" + checkInDate +
                ", CheckOut=" + checkOutDate +
                ", Status='" + status + '\'' +
                ", TotalPrice=" + totalPrice +
                '}';
    }
}
