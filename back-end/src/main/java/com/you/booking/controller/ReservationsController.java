package com.you.booking.controller;

import com.you.booking.dto.ReservationDto;
import com.you.booking.entity.Reservation;
import com.you.booking.exceptions.BusinessException;
import com.you.booking.repository.ReservationRepository;
import com.you.booking.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/reservations")
public class ReservationsController {
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    public ReservationsController(ReservationRepository reservationRepository,ReservationService reservationService){
        this.reservationRepository=reservationRepository;
        this.reservationService=reservationService;
    }

    public ResponseEntity addReservation(ReservationDto reservationDto, Principal principal){
        try {
            return new ResponseEntity<>(this.reservationService.addReservation(reservationDto,principal), HttpStatus.ACCEPTED);
        }catch (BusinessException exception){
            return new ResponseEntity<>(exception.getErrors(), HttpStatus.BAD_REQUEST);
        }
    }

   /* @GetMapping("/")
    public ResponseEntity getClientReservations(){

    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity getClientReservations(){

    }
*/

}
