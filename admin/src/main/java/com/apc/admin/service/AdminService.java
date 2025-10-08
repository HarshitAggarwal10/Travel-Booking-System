package com.apc.admin.service;

import com.apc.admin.repository.AdminRepository;
import com.apc.common.model.Flight;
import com.apc.common.model.Hotel;
import com.apc.common.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "Admin123"; // fixed admin

    private final AdminRepository repo;

    public AdminService(AdminRepository repo) {
        this.repo = repo;
    }

    // ---------------- Admin Login ----------------
    public boolean login(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }

    // ---------------- Users ----------------
    public List<User> getAllUsers() {
        return repo.getAllUsers();
    }

    public boolean deleteUser(int id) {
        return repo.deleteUser(id);
    }

    // ---------------- Flights ----------------
    public List<Flight> getAllFlights() {
        return repo.getAllFlights();
    }

    public Flight addFlight(Flight f) {
        if (f == null) return null;
        if (f.getFlightNumber() == null || f.getFlightNumber().isBlank()) return null;
        if (f.getPrice() <= 0 || f.getAvailableSeats() <= 0) return null;
        return repo.saveFlight(f);
    }

    public Flight updateFlight(Flight f) {
        if (f == null) return null;
        if (f.getId() <= 0) return null;
        return repo.updateFlight(f);
    }

    public boolean deleteFlight(int id) {
        return repo.deleteFlight(id);
    }

    /** List flights in console */
    public void listFlights() {
        List<Flight> flights = getAllFlights();
        if (flights == null || flights.isEmpty()) {
            System.out.println("No flights available.");
            return;
        }
        System.out.println("-- Available Flights --");
        for (Flight f : flights) {
            System.out.printf("%d - %s %s -> %s | Seats: %d | Price: %.2f%n",
                    f.getId(), f.getAirline(), f.getOrigin(), f.getDestination(),
                    f.getAvailableSeats(), f.getPrice());
        }
    }

    // ---------------- Hotels ----------------
    public List<Hotel> getAllHotels() {
        return repo.getAllHotels();
    }

    public Hotel addHotel(Hotel h) {
        if (h == null) return null;
        if (h.getName() == null || h.getName().isBlank()) return null;
        if (h.getPricePerNight() <= 0 || h.getAvailableRooms() <= 0) return null;
        return repo.saveHotel(h);
    }

    public Hotel updateHotel(Hotel h) {
        if (h == null || h.getId() == null) return null;
        return repo.updateHotel(h);
    }

    public boolean deleteHotel(int id) {
        return repo.deleteHotel(id);
    }

    /** List hotels in console */
    public void listHotels() {
        List<Hotel> hotels = getAllHotels();
        if (hotels == null || hotels.isEmpty()) {
            System.out.println("No hotels available.");
            return;
        }
        System.out.println("-- Available Hotels --");
        for (Hotel h : hotels) {
            System.out.printf("%d - %s @%s | Rooms: %d | Price: %.2f | Amenities: %s%n",
                    h.getId(), h.getName(), h.getLocation(),
                    h.getAvailableRooms(), h.getPricePerNight(), h.getAmenities());
        }
    }
}  