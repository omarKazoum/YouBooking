package com.you.booking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomDTO {
    private Long id;
    private String reference ;
    boolean isAvailable;
    private Long hotelId;
    private String HotelTitle;
    private float price;
    public void setIsAvailable(boolean b){
        this.isAvailable=b;
    }
    @JsonProperty("isAvailable")
    public boolean getIsAvailable(){
        return this.isAvailable;
    }
}
