package com.travel.service;

import com.travel.model.Flight;
import java.util.List;

public interface FlightService {
    Flight createFlight(String flightNumber, String airline, String origin, String destination, double price, int availableSeats);
    List<Flight> viewAllFlights();
    List<Flight> searchFlights(String origin, String destination);
    boolean deleteFlight(int id);
}
