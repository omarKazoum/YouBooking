package com.you.booking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Owner extends User{
    protected RoleEnum role = RoleEnum.OWNER;
    @OneToMany(mappedBy = "owner")
    private List<Hotel> hotels=new ArrayList<>();

}
