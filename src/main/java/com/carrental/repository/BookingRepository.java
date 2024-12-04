package com.carrental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrental.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{
    List<Booking> findByCarIdAndStatus(Long carId, String status);

}
