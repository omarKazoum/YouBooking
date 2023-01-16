package com.you.booking.dto;

import com.you.booking.entity.Client;
import com.you.booking.entity.Reservation;
import com.you.booking.entity.Room;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

public class ReservationDto {
    private Long roomId;
    private Long id;
    private String reference;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Client client;
    private Reservation.ReservationStatusEnum status= Reservation.ReservationStatusEnum.CREATED;

}
