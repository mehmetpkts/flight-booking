package com.example.flight_booking.repository;

import com.example.flight_booking.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Passenger entity'si için repository interface'i.
 * Yolcu bilgilerini veritabanında yönetir.
 */
@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}
