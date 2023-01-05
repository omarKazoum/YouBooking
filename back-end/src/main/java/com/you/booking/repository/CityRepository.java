package com.you.booking.repository;

import com.you.booking.entity.City;
import com.you.booking.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Long> {
}
