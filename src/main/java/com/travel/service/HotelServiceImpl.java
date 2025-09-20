package com.travel.service;

import com.travel.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Hotel createHotel(String name, String location, double pricePerNight, int availableRooms, String amenities) {
        String sql = "INSERT INTO hotels (name, location, price_per_night, available_rooms, amenities) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, name, location, pricePerNight, availableRooms, amenities);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM hotels WHERE name=?",
                (rs, rowNum) -> mapHotel(rs),
                name
        );
    }

    @Override
    public List<Hotel> viewAllHotels() {
        return jdbcTemplate.query("SELECT * FROM hotels", (rs, rowNum) -> mapHotel(rs));
    }

    @Override
    public List<Hotel> searchHotels(String location) {
        String sql = "SELECT * FROM hotels WHERE LOWER(location)=LOWER(?)";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapHotel(rs), location);
    }

    @Override
    public boolean deleteHotel(int id) {
        String sql = "DELETE FROM hotels WHERE id=?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private Hotel mapHotel(ResultSet rs) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setId(rs.getInt("id"));
        hotel.setName(rs.getString("name"));
        hotel.setLocation(rs.getString("location"));
        hotel.setPricePerNight(rs.getDouble("price_per_night"));
        hotel.setAvailableRooms(rs.getInt("available_rooms"));
        hotel.setAmenities(rs.getString("amenities"));
        return hotel;
    }
}
