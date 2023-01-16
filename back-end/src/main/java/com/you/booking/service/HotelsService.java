package com.you.booking.service;

import com.you.booking.dto.HotelDTO;
import com.you.booking.exceptions.AuthorisationException;
import com.you.booking.exceptions.BusinessException;
import org.springframework.data.domain.Page;

import java.security.Principal;

public interface HotelsService {
    HotelDTO addHotel(HotelDTO hotelDTO,Principal principal) throws BusinessException;
    Page<HotelDTO> getHotels(Principal principal, int pageIndex, int size) throws AuthorisationException;

    HotelDTO updateHotel(HotelDTO hotelDto, Principal principal) throws BusinessException;

    HotelDTO getHotelById(Long hotelId, Principal principal) throws BusinessException;
}
