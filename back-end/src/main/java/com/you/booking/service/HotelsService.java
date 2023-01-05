package com.you.booking.service;

import com.you.booking.dto.HotelDTO;
import org.springframework.data.domain.Page;

public interface HotelsService {
    HotelDTO addHotel(HotelDTO hotelDTO);
    Page<HotelDTO> getHotels(int pageIndex, int size);
}
