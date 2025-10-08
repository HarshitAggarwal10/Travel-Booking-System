package com.apc.admin.repository;

import com.apc.common.dao.FlightDAO;
import com.apc.common.dao.HotelDAO;
import com.apc.common.dao.UserDAO;
import com.apc.common.model.Flight;
import com.apc.common.model.Hotel;
import com.apc.common.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminRepository {
    private final UserDAO userDAO = new UserDAO();
    private final FlightDAO flightDAO = new FlightDAO();
    private final HotelDAO hotelDAO = new HotelDAO();

    // Users
    public List<User> getAllUsers() { return userDAO.findAll(); }
    public boolean deleteUser(int id) { return userDAO.deleteById(id); }

    // Flights
    public List<Flight> getAllFlights() { return flightDAO.findAll(); }
    public Flight saveFlight(Flight f) { return flightDAO.save(f); }
    public Flight updateFlight(Flight f) { return flightDAO.update(f); }
    public boolean deleteFlight(int id) { return flightDAO.deleteById(id); }

    // Hotels
    public List<Hotel> getAllHotels() { return hotelDAO.findAll(); }
    public Hotel saveHotel(Hotel h) { return hotelDAO.save(h); }
    public Hotel updateHotel(Hotel h) { return hotelDAO.update(h); }
    public boolean deleteHotel(int id) { return hotelDAO.deleteById(id); }
}
