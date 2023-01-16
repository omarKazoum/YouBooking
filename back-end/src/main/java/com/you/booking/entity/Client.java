package com.you.booking.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Client extends User{
    @OneToMany
    private List<Reservation> reservations=new ArrayList<>();
    public Client() {
        setRole(RoleEnum.CLIENT);
    }
}
