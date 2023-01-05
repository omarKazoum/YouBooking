package com.you.booking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.you.booking.entity.City;
import com.you.booking.entity.Owner;
import com.you.booking.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {
    private Long id;
    @JsonIgnore
    private List<RoomDTO> rooms=new ArrayList<>();
    boolean isAvailable;
    boolean isApproved;
    private String title;
    private String description;
    private Long cityId;
    private Long ownerId;
}
