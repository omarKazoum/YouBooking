package com.you.booking.service;

import com.you.booking.dto.RoomDTO;
import com.you.booking.exceptions.BusinessException;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.Date;

public interface RoomsService {
    RoomDTO addRoom(RoomDTO roomDTO, Principal principal) throws BusinessException;
    RoomDTO updateRoom(RoomDTO roomDTO,Principal principal) throws BusinessException;
    void deleteRoom(Long roomId,Principal principal) throws BusinessException;
    Page<RoomDTO> findRoomsByCriteria(Long hotelId, String hotelName, Date fromDate, Date toDate, Long cityId, Principal principal, Integer size, Integer pageIndex);

    Page<RoomDTO> findAll(Principal principal);

    RoomDTO getRoomById(Long roomId, Principal principal) throws BusinessException;

    Object test();
}
