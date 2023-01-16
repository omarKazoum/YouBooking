package com.you.booking.service.implementations;

import com.you.booking.dto.ReservationDto;
import com.you.booking.exceptions.BusinessException;
import com.you.booking.service.ReservationService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Override
    public ReservationDto addReservation(ReservationDto reservationDto, Principal principal) throws BusinessException {
        //validate the reservation then save it then return the corresponding dto
        List<String> errors=new ArrayList<>();

        if(!this.isReservationDtoValid(reservationDto,errors))
            throw new BusinessException("invalid token",errors);

        return null;
    }

    private boolean isReservationDtoValid(ReservationDto reservationDto, List<String> errors) {
        //TODO validate dto for any non logical input
        return true;
    }
}
