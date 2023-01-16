package com.you.booking.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Administrator extends User{
    protected RoleEnum role = RoleEnum.ADMIN;

}
