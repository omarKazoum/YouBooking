package com.you.booking.service.implementations;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.you.booking.dto.HotelDTO;
import com.you.booking.dto.RoomDTO;
import com.you.booking.entity.Hotel;
import com.you.booking.entity.Owner;
import com.you.booking.entity.Room;
import com.you.booking.repository.CityRepository;
import com.you.booking.repository.HotelRepository;
import com.you.booking.repository.RoomRepository;
import com.you.booking.repository.UserRepository;
import com.you.booking.service.HotelsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HotelsServiceImpl implements HotelsService {
    final HotelRepository hotelRepository;
    final CityRepository cityRepository;
    final UserRepository userRepository;
    final RoomRepository roomRepository;

    HotelsServiceImpl(HotelRepository hotelRepository, CityRepository cityRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.cityRepository = cityRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public HotelDTO addHotel( HotelDTO hotelDTO) {
        Hotel hotelToSave = new Hotel();
        hotelToSave.setApproved(false);
        hotelToSave.setAvailable(false);
        //TODO validate that the city with id actually exists
        hotelToSave.setCity(cityRepository.findById(hotelDTO.getCityId()).get());
        hotelToSave.setTitle(hotelDTO.getTitle());
        hotelToSave.setDescription(hotelDTO.getDescription());
        //TODO validate the owner id that it's actually an owner
        Owner owner = (Owner) userRepository.findById(hotelDTO.getOwnerId()).get();
        hotelToSave.setOwner(owner);
        Hotel savedHotel = hotelRepository.save(hotelToSave);
        hotelToSave.setRooms(hotelDTO.getRooms().stream().map(
                        roomDTO -> {
                            Room roomToSave = new Room(null, roomDTO.getReference(), roomDTO.isAvailable(), savedHotel, null);
                            return roomRepository.save(roomToSave);
                        }
                ).collect(Collectors.toList())
        );
        hotelRepository.save(hotelToSave);
        hotelDTO.setId(savedHotel.getId());
        hotelDTO.setRooms(savedHotel.getRooms().stream().map(room -> {
            return new RoomDTO(room.getId(),room.getReference(),room.isAvailable(),room.getHotel().getId());
        }).collect(Collectors.toList()));
        return hotelDTO;
    }

    @Override
    public Page<HotelDTO> getHotels(int pageIndex, int size) {
        Page<Hotel> hotelPage=hotelRepository.findAll(PageRequest.of(pageIndex, size));

        return new PageImpl<>(hotelPage.stream().map(
                h ->
                        new HotelDTO(h.getId(),null,h.isAvailable(),h.isApproved(),h.getTitle(),h.getDescription(),h.getCity().getId(),h.getOwner().getId())).collect(Collectors.toList())
                ,PageRequest.of(pageIndex, size)
                ,hotelPage.getTotalElements());
    }
}
