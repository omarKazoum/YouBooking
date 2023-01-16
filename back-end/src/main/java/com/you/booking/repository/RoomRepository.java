package com.you.booking.repository;

import com.you.booking.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.Date;

public interface RoomRepository extends JpaRepository<Room,Long>, JpaSpecificationExecutor<Room> {
    Page<Room> findAllByReservationsFromDateAfterAndReservationsToDateBefore(LocalDate fromDate, LocalDate toDate, Pageable pageable);
}
