package com.you.booking.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Type;

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
    private String imageName;
    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] imageBase64;
    @ManyToOne
    private City city;
    @ManyToOne
    private Owner owner;
}
