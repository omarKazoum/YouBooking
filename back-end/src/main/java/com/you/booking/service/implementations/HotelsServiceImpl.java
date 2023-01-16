package com.you.booking.service.implementations;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.you.booking.dto.HotelDTO;
import com.you.booking.dto.RoomDTO;
import com.you.booking.entity.*;
import com.you.booking.exceptions.AuthorisationException;
import com.you.booking.exceptions.BusinessException;
import com.you.booking.repository.CityRepository;
import com.you.booking.repository.HotelRepository;
import com.you.booking.repository.RoomRepository;
import com.you.booking.repository.UserRepository;
import com.you.booking.service.HotelsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public HotelDTO addHotel( HotelDTO hotelDTO,Principal principal) throws BusinessException {
        List<String> errors=new ArrayList<>();

        if(!isHotelDtoValid(hotelDTO,errors))
            throw new BusinessException("invalid token",errors);

        Optional<User> optionalConnectedUser=userRepository.findByEmail(principal.getName());
        if(optionalConnectedUser.isEmpty())
            throw new  IllegalStateException("this endpoint must be protected");
        Hotel hotelToSave = new Hotel();
        hotelToSave.setApproved(false);
        hotelToSave.setAvailable(false);
        hotelToSave.setImageBase64(hotelDTO.getImageBase64().getBytes());
        hotelToSave.setImageName(hotelDTO.getImageName());
        hotelToSave.setCity(cityRepository.findById(hotelDTO.getCityId()).get());
        hotelToSave.setTitle(hotelDTO.getTitle());
        hotelToSave.setDescription(hotelDTO.getDescription());
        Owner owner;

        if(optionalConnectedUser.get().getRole().equals(RoleEnum.OWNER))
         owner= (Owner) optionalConnectedUser.get();
        else
            owner= (Owner) userRepository.findById(hotelDTO.getId()).get();

        hotelToSave.setOwner(owner);
        Hotel savedHotel = hotelRepository.save(hotelToSave);
        hotelToSave.setRooms(hotelDTO.getRooms().stream().map(
                        roomDTO -> {
                            Room roomToSave = new Room(null, roomDTO.getReference(), roomDTO.getIsAvailable(),roomDTO.getPrice(), savedHotel, null);
                            return roomRepository.save(roomToSave);
                        }
                ).collect(Collectors.toList())
        );
        hotelRepository.save(hotelToSave);
        hotelDTO.setId(savedHotel.getId());
        hotelDTO.setOwnerName(hotelToSave.getOwner().getFirstName()+" "+hotelToSave.getOwner().getLastName());
        hotelDTO.setRooms(savedHotel.getRooms().stream().map(room -> {
            return new RoomDTO(room.getId(),room.getReference(),room.isAvailable(),room.getHotel().getId(),room.getHotel().getTitle(),room.getPrice());
        }).collect(Collectors.toList()));
        return hotelDTO;
    }


    @Override
    public Page<HotelDTO> getHotels(Principal principal, int pageIndex, int size) throws AuthorisationException {
        Optional<User> optionalUser=userRepository.findByEmail(principal.getName());
        if(optionalUser.isEmpty())
            throw new AuthorisationException("this endpoint should be protected!");
        Page<Hotel> hotelPage;
        if(optionalUser.get().getRole().equals(RoleEnum.OWNER)){
            hotelPage=hotelRepository.findByOwnerId(optionalUser.get().getId(), PageRequest.of(pageIndex, size));
        }else if(optionalUser.get().getRole().equals(RoleEnum.ADMIN)){
            hotelPage=hotelRepository.findAll(PageRequest.of(pageIndex, size));
        }else{
            //if the user is a client he should only see approved and available hotels
            hotelPage=hotelRepository.findByIsApprovedTrueAndIsAvailableTrue(PageRequest.of(pageIndex, size));
        }

        return new PageImpl<>(hotelPage.stream().map(
                h ->
                        new HotelDTO(
                                h.getId(),
                                null,
                                h.getImageName(),
                                new String(h.getImageBase64()),
                                h.isAvailable(),
                                h.isApproved(),
                                h.getTitle(),
                                h.getDescription(),
                                h.getCity().getId(),
                                h.getOwner().getFirstName()+" "+h.getOwner().getLastName())
        ).collect(Collectors.toList())
                ,PageRequest.of(pageIndex, size)
                ,hotelPage.getTotalElements());
    }

    @Override
    public HotelDTO updateHotel(HotelDTO hotelDTO, Principal principal) throws BusinessException {
        List<String> errors=new ArrayList<>();

        if(!isHotelDtoValid(hotelDTO,errors))
            throw new BusinessException("invalid token",errors);
    
        Optional<User> optionalConnectedUser=userRepository.findByEmail(principal.getName());
        if(optionalConnectedUser.isEmpty())
            throw new  IllegalStateException("this endpoint must be protected");
        Optional<Hotel> optionalHotel=hotelRepository.findById(hotelDTO.getId());
        if(optionalHotel.isEmpty())
            throw new BusinessException("invalid token", Arrays.asList("invalid hotel id"));

        Hotel hotelToSave = optionalHotel.get();
        hotelToSave.setAvailable(hotelDTO.getIsAvailable());
        //only update hotel approval status if the user is admin
        if(optionalConnectedUser.get().getRole().equals(RoleEnum.ADMIN))
            hotelToSave.setApproved(hotelDTO.getIsApproved());

        hotelToSave.setCity(cityRepository.findById(hotelDTO.getCityId()).get());
        hotelToSave.setTitle(hotelDTO.getTitle());
        hotelToSave.setDescription(hotelDTO.getDescription());
        Hotel savedHotel = hotelRepository.save(hotelToSave);
        hotelToSave.setRooms(hotelDTO.getRooms().stream().map(
                        roomDTO -> {
                            //todo update this for update
                            Room roomToSave = new Room(null, roomDTO.getReference(),roomDTO.getIsAvailable(), roomDTO.getPrice(), savedHotel, null);
                            return roomRepository.save(roomToSave);
                        }
                ).collect(Collectors.toList())
        );
        hotelRepository.save(hotelToSave);
        hotelDTO.setId(savedHotel.getId());
        hotelDTO.setOwnerName(hotelToSave.getOwner().getFirstName()+" "+hotelToSave.getOwner().getLastName());
        hotelDTO.setRooms(savedHotel.getRooms().stream().map(room -> {
            return new RoomDTO(room.getId(),room.getReference(),room.isAvailable(),room.getHotel().getId(),room.getHotel().getTitle(),room.getPrice());
        }).collect(Collectors.toList()));
        return hotelDTO;
    }

    @Override
    public HotelDTO getHotelById(Long hotelId, Principal principal) throws BusinessException {
        Optional<Hotel> optionalHotel=hotelRepository.findById(hotelId);
        if(optionalHotel.isEmpty())
            throw new BusinessException("hotel not found");
            Hotel h=optionalHotel.get();
        return new HotelDTO(h.getId(),null,h.getImageName(),new String(h.getImageBase64()),h.isAvailable(),h.isApproved(),h.getTitle(),h.getDescription(),h.getCity().getId(),h.getOwner().getFirstName()+" "+h.getOwner().getLastName());
    }

    private boolean isHotelDtoValid(HotelDTO hotelDto, List<String> errors){
        //TODO implement this for validating hotelDto and append any validation errors that may occur
        //TODO validate that the city with id actually exists
        //TODO validate the owner id that it's actually an owner

        return true;
    }
}
