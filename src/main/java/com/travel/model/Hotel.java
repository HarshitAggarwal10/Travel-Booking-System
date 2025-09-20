package com.travel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private double pricePerNight;

    @Column(nullable = false)
    private int availableRooms;

    private String amenities;

    public Hotel() {}

    public Hotel(String name, String location, double pricePerNight, int availableRooms, String amenities) {
        this.name = name;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.availableRooms = availableRooms;
        this.amenities = amenities;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; }

    public int getAvailableRooms() { return availableRooms; }
    public void setAvailableRooms(int availableRooms) { this.availableRooms = availableRooms; }

    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }

    @Override
    public String toString() {
        return "Hotel{" +
                "ID=" + id +
                ", Name='" + name + '\'' +
                ", Location='" + location + '\'' +
                ", PricePerNight=" + pricePerNight +
                ", AvailableRooms=" + availableRooms +
                ", Amenities='" + amenities + '\'' +
                '}';
    }
}
