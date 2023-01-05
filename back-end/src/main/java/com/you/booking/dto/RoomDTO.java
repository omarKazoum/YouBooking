package com.you.booking.dto;

import com.you.booking.entity.Hotel;
import com.you.booking.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
public class RoomDTO {
    private Long id;
    private String reference ;
    boolean isAvailable=true;
    private Long hotelId;
}
