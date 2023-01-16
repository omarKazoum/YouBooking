package com.you.booking.service.implementations;

import com.you.booking.entity.City;
import com.you.booking.repository.CityRepository;
import com.you.booking.service.SelectionsService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SelectionsServiceImpl implements SelectionsService {
    private final CityRepository cityRepository;

    public SelectionsServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }
    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }
}
