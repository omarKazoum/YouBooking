package com.you.booking.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Data
@NoArgsConstructor
public class Reservation {
    @ManyToOne
    private Room room;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String reference;
    private LocalDate fromDate;
    private LocalDate toDate;
    @ManyToOne
    private Client client;
    private ReservationStatusEnum status=ReservationStatusEnum.CREATED;

    private enum ReservationStatusEnum {
        CREATED,CONFIRMED,CANCELED,REJECTED;
    }
}
