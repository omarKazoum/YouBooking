package com.you.booking.service;

import com.you.booking.dto.ReservationDto;
import com.you.booking.exceptions.BusinessException;

import java.security.Principal;

public interface ReservationService {
    ReservationDto addReservation(ReservationDto reservationDto, Principal principal) throws BusinessException;
}
