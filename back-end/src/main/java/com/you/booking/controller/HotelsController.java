package com.you.booking.controller;


import com.you.booking.dto.HotelDTO;
import com.you.booking.exceptions.AuthorisationException;
import com.you.booking.exceptions.BusinessException;
import com.you.booking.service.HotelsService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api/hotels")
public class HotelsController {
    final HotelsService hotelsService;
    public HotelsController(HotelsService hotelsService){
        this.hotelsService=hotelsService;
    }
    //create hotel
    @PostMapping("/")
    ResponseEntity addHotel(@RequestBody HotelDTO hotelDto, Principal principal){
        try {
            return new ResponseEntity<>(hotelsService.addHotel(hotelDto, principal), HttpStatus.ACCEPTED);
        }catch (BusinessException exception){
            return new ResponseEntity<>(exception.getErrors(), HttpStatus.BAD_REQUEST);
        }
    }
   @GetMapping("/")
   Page<HotelDTO> getHotels(Principal principal, @RequestParam(value = "page",required = false,defaultValue = "0") int pageIndex,
                            @RequestParam(name = "size", required = false,defaultValue = "0") Integer size) throws AuthorisationException {
        if(size.intValue()==0){
            size=Integer.MAX_VALUE;
        }
        return hotelsService.getHotels(principal,pageIndex,size);

    }
    @PutMapping("/")
    ResponseEntity update(@RequestBody HotelDTO hotelDto,Principal principal){
        //approve added hotel by admin
        //update hotel by owner or admin (only admin can change approval status)
        try {
                return new ResponseEntity<>(hotelsService.updateHotel(hotelDto,principal),HttpStatus.ACCEPTED);
        }catch (BusinessException exception){
            return new ResponseEntity<>(exception.getErrors(), HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("/{hotelId}")
    ResponseEntity getHotelById(@PathVariable("hotelId") Long hotelId,Principal principal){
        try {
            return new ResponseEntity<>(hotelsService.getHotelById(hotelId, principal), HttpStatus.ACCEPTED);
        }catch (BusinessException exception){
            return new ResponseEntity<>(exception.getErrors(), HttpStatus.BAD_REQUEST);
        }
    }

    //approve added hotel by admin
    //show available hotels only approved hotels for clients
    //reserve a room

}
