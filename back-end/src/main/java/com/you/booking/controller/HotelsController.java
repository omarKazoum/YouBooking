package com.you.booking.controller;


import com.you.booking.dto.HotelDTO;
import com.you.booking.service.HotelsService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels")
public class HotelsController {
    final HotelsService hotelsService;
    public HotelsController(HotelsService hotelsService){
        this.hotelsService=hotelsService;
    }
    //create hotel
    @PostMapping("/")
    ResponseEntity<HotelDTO> addHotel(@RequestBody HotelDTO hotelDto){
        return new ResponseEntity<>(hotelsService.addHotel(hotelDto),HttpStatus.ACCEPTED);

    }
   @GetMapping("/")
   Page<HotelDTO> getHotels(@RequestParam(value = "page",required = false,defaultValue = "0") int pageIndex,
                                                   @RequestParam(name = "size", required = false,defaultValue = "5") int size){
        return hotelsService.getHotels(pageIndex,size);

    }
    //show hotels for all users
    //approve added hotel by admin
    //show available hotels
    //reserve a room

}
