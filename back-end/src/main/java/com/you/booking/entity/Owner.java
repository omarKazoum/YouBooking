package com.you.booking.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Owner extends User{
    protected RoleEnum role = RoleEnum.OWNER;
    @OneToMany(mappedBy = "owner")
    private List<Hotel> hotels=new ArrayList<>();

}
