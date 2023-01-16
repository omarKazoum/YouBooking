package com.you.booking.service.implementations;

import com.you.booking.dto.RoomDTO;
import com.you.booking.entity.*;
import com.you.booking.exceptions.BusinessException;
import com.you.booking.repository.HotelRepository;
import com.you.booking.repository.ReservationRepository;
import com.you.booking.repository.RoomRepository;
import com.you.booking.repository.UserRepository;
import com.you.booking.service.RoomsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomsServiceImpl implements RoomsService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository usersRepository;
    private final ReservationRepository reservationRepository;


    public RoomsServiceImpl(RoomRepository roomRepository, HotelRepository hotelRepository, UserRepository usersRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.usersRepository = usersRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public RoomDTO addRoom(RoomDTO roomDTO, Principal principal) throws BusinessException {
        List<String> errors=new ArrayList<>();
        if(!isRoomDtoValid(roomDTO,errors))
            throw new BusinessException("invalid token",errors);
        Room roomToAdd=new Room();
        roomToAdd.setAvailable(true);
        roomToAdd.setReference(roomDTO.getReference());
        roomToAdd.setHotel(hotelRepository.findById(roomDTO.getHotelId()).get());
        roomToAdd.setPrice(roomDTO.getPrice());
        roomToAdd=roomRepository.save(roomToAdd);
        roomDTO.setId(roomToAdd.getId());
        return roomDTO;
    }

    @Override
    public RoomDTO updateRoom(RoomDTO roomDTO, Principal principal) throws BusinessException {
        List<String> errors=new ArrayList<>();
        if(!isRoomDtoValid(roomDTO,errors))
            throw new BusinessException("invalid token",errors);
        Room roomToAdd=roomRepository.findById(roomDTO.getId()).get();
        roomToAdd.setAvailable(true);
        roomToAdd.setReference(roomDTO.getReference());
        roomToAdd.setHotel(hotelRepository.findById(roomDTO.getHotelId()).get());
        roomToAdd.setPrice(roomDTO.getPrice());
        roomToAdd=roomRepository.save(roomToAdd);
        roomDTO.setId(roomToAdd.getId());
        return roomDTO;
    }

    @Override
    public void deleteRoom(Long roomId, Principal principal) throws BusinessException {
        Optional<Room> optionalRoom=roomRepository.findById(roomId);
        if(optionalRoom.isEmpty())
            throw new BusinessException("invalid room");
        //TODO also check that the room belongs to one of this user's hotels
        roomRepository.deleteById(roomId);
    }



    @Override
    public Page<RoomDTO> findRoomsByCriteria(Long hotelId,
                                             String hotelName,
                                             Date fromDate,
                                             Date toDate,
                                             Long cityId,
                                             Principal principal,
                                             Integer size,
                                             Integer pageIndex) {
        Optional<User> optionalUser=usersRepository.findByEmail(principal.getName());

        Page<Room> page;
        if(hotelId==null && (hotelName==null ||hotelName.equals("")) && fromDate==null && toDate==null && cityId==null) {
            // if connected user is an owner only show him his own rooms
            switch(optionalUser.get().getRole()){
                case OWNER :
                    page=roomRepository.findAll(hasOwner((Owner) optionalUser.get()),PageRequest.of(pageIndex,size));
                    break;
                default:
                    page = roomRepository.findAll(PageRequest.of(pageIndex,size));
            }
        }
        //dateFrom and toDate and required
        List<Long> ids=this.reservationRepository.findByFromDateBeforeAndToDateAfter(convertToLocalDateViaInstant(fromDate),convertToLocalDateViaInstant(toDate)).stream().map(r->r.getRoom().getId()).distinct().collect(Collectors.toList());
        if(ids.isEmpty())
            ids.add(0l);
        Specification<Room> criteria=hasIdNotIn(ids);
        if(hotelId!=null)
            criteria=criteria.and(hasHotelId(hotelId));
        if(hotelName!=null)
            criteria=criteria.and(hasHotelName(hotelName));
        if(cityId!=null)
            criteria=criteria.and(hasCityId(cityId));

        page=roomRepository.findAll(criteria,PageRequest.of(pageIndex,size));
        //page=roomRepository.findAllByReservationsFromDateAfterAndReservationsToDateBefore(fromDate,toDate,PageRequest.of(pageIndex,size));
        return page.map(r->new RoomDTO(r.getId(),r.getReference(),r.isAvailable(),r.getHotel().getId(),r.getHotel().getTitle(),r.getPrice()));
    }

    @Override
    public Page<RoomDTO> findAll(Principal principal) {
       /* Optional<User> optionalUser=usersRepository.findByEmail(principal.getName());
        RoleEnum connectedUserRole=optionalUser.get().getRole();
        switch(connectedUserRole){
            case OWNER :
                //only return rooms from his own hotels
                return
                break;
            case ADMIN:

                break;
            case CLIENT:

                break;
            default:
                throw new IllegalStateException("user without role cannot exist");
        }*/
        return null;
    }

    @Override
    public RoomDTO getRoomById(Long roomId, Principal principal) throws BusinessException {
        Optional<Room> optionalRoom=roomRepository.findById(roomId);
        if(optionalRoom.isEmpty())
            throw new BusinessException("invalid room id");
        Room room=optionalRoom.get();
        RoomDTO roomDTO=new RoomDTO(room.getId(),room.getReference(),room.isAvailable(),room.getHotel().getId(),room.getHotel().getTitle(),room.getPrice());
        return roomDTO;
    }

    @Override
    public Object test() {
        return roomRepository.findAll(hasIdNotIn(Arrays.asList(10l)));
    }
//    Long hotelId,
//    String hotelName,
//    Date fromDate,
//    Date toDate,
//    Long cityId,

    private Specification<Room> hasIdNotIn(List<Long> ids){
        return (room,criteriaQuery,criteriaBuilder)->
                room.get("id").in(ids).not();
    }


    private Specification<Room> hasOwner(Owner owner){
        return (room,criteriaQuery,criteriaBuilder)-> {
            Join<Room, Hotel> roomHotelJoin=room.join("hotel");
            return criteriaBuilder.equal(roomHotelJoin.get("owner"), owner);
        };
    }
    private Specification<Room> hasHotelName(String  hotelName){
        return (room,criteriaQuery,criteriaBuilder)-> {
            Join<Room, Hotel> roomHotelJoin=room.join("hotel");
            return criteriaBuilder.equal(roomHotelJoin.get("title"), hotelName);
        };
    }
    private Specification<Room> hasHotelId(Long  hotelId){
        return (room,criteriaQuery,criteriaBuilder)-> {
            Join<Room, Hotel> roomHotelJoin=room.join("hotel");
            return criteriaBuilder.equal(roomHotelJoin.get("id"), hotelId);
        };
    }
    private Specification<Room> hasCityId(Long  cityId){
        return (room,criteriaQuery,criteriaBuilder)-> {
            Join<Room, City> roomHotelJoin=room.join("hotel").join("city");
            return criteriaBuilder.equal(roomHotelJoin.get("id"), cityId);
        };
    }

    private boolean isRoomDtoValid(RoomDTO roomDTO, List<String> errors){
        //TODO validate dto here
        boolean isValid=true;
        if(!hotelRepository.existsById(roomDTO.getHotelId()))
        {
            errors.add("invalid hotel id");
            isValid=false;
        }
        if(roomDTO.getId() !=null && !roomRepository.existsById(roomDTO.getId())){
            errors.add("invalid room id");
            isValid=false;
        }

        return isValid;
    }
    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
