package com.travel.repository;

import com.travel.model.Flight;
import java.util.ArrayList;
import java.util.List;

public class FlightRepository {
    private final List<Flight> flights = new ArrayList<>();
    private int idCounter = 1;

    public Flight save(Flight flight) {
        if (flight.getId() == 0) {
            flight.setId(idCounter++);
            flights.add(flight);
        } else {
            flights.replaceAll(f -> f.getId() == flight.getId() ? flight : f);
        }
        return flight;
    }

    public Flight findById(int id) {
        return flights.stream().filter(f -> f.getId() == id).findFirst().orElse(null);
    }

    public List<Flight> findAll() {
        return new ArrayList<>(flights);
    }

    public List<Flight> findByOriginAndDestination(String origin, String destination) {
        return flights.stream()
                .filter(f -> f.getOrigin().equalsIgnoreCase(origin) &&
                        f.getDestination().equalsIgnoreCase(destination))
                .toList();
    }

    public boolean delete(int id) {
        return flights.removeIf(f -> f.getId() == id);
    }

    public boolean existsById(int id) {
        throw new UnsupportedOperationException("Unimplemented method 'existsById'");
    }
}
