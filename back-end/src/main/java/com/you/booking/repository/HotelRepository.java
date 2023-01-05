package com.you.booking.repository;

import com.you.booking.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HotelRepository extends JpaRepository<Hotel,Long>, JpaSpecificationExecutor<Hotel> {
}
