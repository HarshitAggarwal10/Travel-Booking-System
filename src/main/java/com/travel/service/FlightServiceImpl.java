package com.travel.service;

import com.travel.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Flight createFlight(String flightNumber, String airline, String origin, String destination, double price, int availableSeats) {
        String sql = "INSERT INTO flights (flight_number, airline, origin, destination, price, available_seats) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, flightNumber, airline, origin, destination, price, availableSeats);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM flights WHERE flight_number=?",
                (rs, rowNum) -> mapFlight(rs),
                flightNumber
        );
    }

    @Override
    public List<Flight> viewAllFlights() {
        return jdbcTemplate.query("SELECT * FROM flights", (rs, rowNum) -> mapFlight(rs));
    }

    @Override
    public List<Flight> searchFlights(String origin, String destination) {
        String sql = "SELECT * FROM flights WHERE LOWER(origin)=LOWER(?) AND LOWER(destination)=LOWER(?)";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapFlight(rs), origin, destination);
    }

    @Override
    public boolean deleteFlight(int id) {
        String sql = "DELETE FROM flights WHERE id=?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private Flight mapFlight(ResultSet rs) throws SQLException {
        Flight flight = new Flight();
        flight.setId(rs.getInt("id"));
        flight.setFlightNumber(rs.getString("flight_number"));
        flight.setAirline(rs.getString("airline"));
        flight.setOrigin(rs.getString("origin"));
        flight.setDestination(rs.getString("destination"));
        flight.setPrice(rs.getDouble("price"));
        flight.setAvailableSeats(rs.getInt("available_seats"));
        return flight;
    }
}
