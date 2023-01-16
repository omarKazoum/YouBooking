package com.you.booking.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HotelDTO {
    public HotelDTO(Long id,
                    List<RoomDTO> rooms,
                    String imageName,
                    String imageBase64,
                    boolean isAvailable,
                    boolean isApproved,
                    String title,
                    String description,
                    Long cityId,
                    String ownerName) {
        this.id = id;
        this.rooms = rooms;
        this.imageName = imageName;
        this.imageBase64 = imageBase64;
        this.isAvailable = isAvailable;
        this.isApproved = isApproved;
        this.title = title;
        this.description = description;
        this.cityId = cityId;
        this.ownerName = ownerName;
    }

    private Long id;
    @JsonIgnore
    private List<RoomDTO> rooms=new ArrayList<>();
    private String imageName;
    private String imageBase64;
    boolean isAvailable;
    boolean isApproved;
    private String title;
    private String description;
    private Long cityId;
    private String ownerName;
    public void setIsAvailable(boolean b){
        this.isAvailable=b;
    }
    public void setIsApproved(boolean b){
        this.isApproved=b;
    }
    @JsonProperty("isAvailable")
    public boolean getIsAvailable(){
       return this.isAvailable;
    }
    @JsonProperty("isApproved")
    public boolean getIsApproved(){
        return this.isApproved;
    }


}
