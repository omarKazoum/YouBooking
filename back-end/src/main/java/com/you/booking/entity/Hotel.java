package com.you.booking.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms=new ArrayList<>();
    boolean isAvailable;
    boolean isApproved;
    private String title;
    private String description;
    @ManyToOne
    private City city;
    @ManyToOne
    private Owner owner;
}
