package com.you.booking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String reference ;
    boolean isAvailable=true;
    float price=0f;
    @ManyToOne
    @JsonIgnore
    private Hotel hotel;
    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private List<Reservation> reservations=new ArrayList<>();
}
