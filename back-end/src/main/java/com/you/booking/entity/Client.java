package com.you.booking.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Client extends User{
    @OneToMany
    private List<Reservation> reservations=new ArrayList<>();
    public Client() {
        setRole(RoleEnum.CLIENT);
    }
}
