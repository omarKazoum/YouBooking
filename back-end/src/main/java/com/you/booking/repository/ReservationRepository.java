package com.you.booking.repository;

import com.you.booking.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("SELECT r FROM Reservation r WHERE r.fromDate>=:fromDate AND r.toDate<=:toDate")
    List<Reservation> findByFromDateBeforeAndToDateAfter(LocalDate fromDate, LocalDate toDate);
}
