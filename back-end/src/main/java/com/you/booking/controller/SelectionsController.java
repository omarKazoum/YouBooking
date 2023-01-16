package com.you.booking.controller;

import com.you.booking.entity.City;
import com.you.booking.service.SelectionsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/selections")
public class SelectionsController {
    private final SelectionsService selectionsService;

    public SelectionsController(SelectionsService selectionsService) {
        this.selectionsService = selectionsService;
    }

    @GetMapping("/cities")
    public List<City> getCities(){
        return selectionsService.getAllCities();
    }
}
