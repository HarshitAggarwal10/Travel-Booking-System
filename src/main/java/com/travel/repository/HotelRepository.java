package com.travel.repository;

import com.travel.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    // Search hotels by location (case-insensitive)
    List<Hotel> findByLocationIgnoreCase(String location);
}
