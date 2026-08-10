package com.example.flight_booking.repository;

import com.example.flight_booking.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Aircraft entity'si için repository interface'i.
 * Uçak bilgilerini veritabanında yönetir.
 */
@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

}
