package com.example.flight_booking.repository;

import com.example.flight_booking.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Airport entity'si için repository interface'i.
 * Havaalanı bilgilerini veritabanında yönetir.
 */
@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

}
