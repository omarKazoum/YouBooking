package com.you.booking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import com.you.booking.entity.RoleEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Pattern;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoginDTO {
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
    private String email;
    private String password;
    private String jwtToken;
}
