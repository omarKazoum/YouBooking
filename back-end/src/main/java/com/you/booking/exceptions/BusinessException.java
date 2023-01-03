package com.you.booking.exceptions;

import lombok.Data;

import java.util.List;

@Data
public class BusinessException extends Exception{
    List<String> errors;

    public BusinessException(String message, List<String> errors){
        super(message);
        this.errors=errors;
    }
    public BusinessException(String message){
        super(message);
        this.errors=errors;
    }
}
