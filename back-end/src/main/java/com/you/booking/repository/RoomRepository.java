package com.you.booking.repository;

import com.you.booking.entity.City;
import com.you.booking.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {
}
