package com.you.booking.security;

import com.you.booking.entity.RoleEnum;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecurityUtils {
    public static Collection<SimpleGrantedAuthority> getAuthoritiesForRole(RoleEnum role){
        //TODO:: edit this according to this brief logic
        List<SimpleGrantedAuthority> authorityList=new ArrayList<>();
        switch (role){
            case CLIENT:
                authorityList.add(new SimpleGrantedAuthority(Authorities.MANAGE_ORDERS))
                ;break;

            case ADMIN:
                authorityList.add(new SimpleGrantedAuthority(Authorities.MANAGE_PRODUCTS));
                authorityList.add(new SimpleGrantedAuthority(Authorities.MANAGE_ORDERS))
                ;break;
        }
        return authorityList;

    }
}
