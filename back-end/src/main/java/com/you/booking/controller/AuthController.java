package com.you.booking.controller;

import com.you.booking.dto.LoginDTO;
import com.you.booking.exceptions.RegisterException;
import com.you.booking.repository.UserRepository;
import com.you.booking.service.UserDetailsService;
import com.you.booking.dto.UserRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserRepository userRepository;
    @PostMapping("/register")
    ResponseEntity register(@RequestBody UserRegisterDTO userDTO){
        try{
            UserRegisterDTO userRegisterDTO=userDetailsService.register(userDTO);
            return new ResponseEntity<>(userRegisterDTO, HttpStatus.ACCEPTED);

        }catch (RegisterException exception){
            exception.printStackTrace();
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        try {
            return new ResponseEntity<>(userDetailsService.login(loginDTO), HttpStatus.ACCEPTED);
        }catch (Exception exception){
            return new ResponseEntity<>("Invalid credentials", HttpStatus.BAD_REQUEST);
        }
    }


}
