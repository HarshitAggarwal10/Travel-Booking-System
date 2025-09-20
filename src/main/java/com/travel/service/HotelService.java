package com.travel.service;

import com.travel.model.Hotel;
import java.util.List;

public interface HotelService {
    Hotel createHotel(String name, String location, double pricePerNight, int availableRooms, String amenities);
    List<Hotel> viewAllHotels();
    List<Hotel> searchHotels(String location);
    boolean deleteHotel(int id);
}
