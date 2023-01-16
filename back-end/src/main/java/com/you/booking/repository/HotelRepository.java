package com.you.booking.repository;

import com.you.booking.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel,Long>, JpaSpecificationExecutor<Hotel> {
    Page<Hotel> findByOwnerId(Long ownerId, Pageable pageable);
    Page<Hotel> findByApprovedAndAvailable(boolean isApproved,boolean isAvailable, Pageable pageable);
    Page<Hotel> findByIsApprovedTrueAndIsAvailableTrue( Pageable pageable);


}
