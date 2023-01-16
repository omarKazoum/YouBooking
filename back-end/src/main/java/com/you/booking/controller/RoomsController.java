package com.you.booking.controller;

import com.you.booking.dto.RoomDTO;
import com.you.booking.exceptions.BusinessException;
import com.you.booking.service.RoomsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping("/api/rooms")
public class RoomsController {
    final RoomsService roomsService;

    public RoomsController(RoomsService roomsService) {
        this.roomsService = roomsService;
    }

    @PostMapping("/")
    ResponseEntity addRoom(@RequestBody RoomDTO roomDTO, Principal principal){
        try {
            return new ResponseEntity<>(roomsService.addRoom(roomDTO, principal), HttpStatus.ACCEPTED);
        }catch (BusinessException exception){
            return new ResponseEntity<>(exception.getErrors(), HttpStatus.CONFLICT  );
        }
    }
    @PutMapping("/")
    ResponseEntity updateRoom(@RequestBody RoomDTO roomDTO, Principal principal){
        try {
            return new ResponseEntity<>(roomsService.updateRoom(roomDTO, principal), HttpStatus.ACCEPTED);
        }catch (BusinessException exception){
            return new ResponseEntity<>(exception.getErrors(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{roomId}")
    ResponseEntity deleteRoom(@PathVariable("roomId") Long roomId, Principal principal){
        try {
            roomsService.deleteRoom(roomId, principal);
            return new ResponseEntity<>("Room deleted!", HttpStatus.ACCEPTED);
        }catch (BusinessException exception){
            return new ResponseEntity<>(exception.getErrors(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{roomId}")
    ResponseEntity getHotelById(@PathVariable("roomId") Long roomId,Principal principal){
        try {
            return new ResponseEntity<>(roomsService.getRoomById(roomId, principal), HttpStatus.ACCEPTED);
        }catch (BusinessException exception){
            return new ResponseEntity<>(exception.getErrors(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    ResponseEntity findAll(Principal principal){
        return new ResponseEntity(this.roomsService.findAll(principal),HttpStatus.OK);
    }
    @GetMapping("/")
    ResponseEntity findByCriteria(Principal principal,
                                  @RequestParam(required = false,value = "hotelId") Long hotelId,
                                  @RequestParam(required = false,value = "hotelName") String hotelName,
                                  @RequestParam(required = false,value = "fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                                  @RequestParam(required = false,value = "toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
                                  @RequestParam(required = false,value = "cityId") Long cityId,
                                  @RequestParam(value = "page",required = false,defaultValue = "0") Integer pageIndex,
                                  @RequestParam(name = "size", required = false,defaultValue = "0") Integer size
                                  ){
        if(size.intValue()==0){
            size=Integer.MAX_VALUE;
        }
        return new ResponseEntity(this.roomsService.findRoomsByCriteria(hotelId,hotelName, fromDate,toDate, cityId, principal,size,pageIndex),HttpStatus.OK);
    }

    @GetMapping("/test")
    ResponseEntity test(){
        return new ResponseEntity<>(roomsService.test(),HttpStatus.ACCEPTED);
    }
}
