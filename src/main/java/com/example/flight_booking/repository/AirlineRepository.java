package com.example.flight_booking.repository;

import com.example.flight_booking.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Airline entity'si için repository interface'i.
 * Havayolu şirketi bilgilerini veritabanında yönetir.
 */
@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long> {

}
