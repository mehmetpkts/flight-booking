package com.example.flight_booking.repository;

import com.example.flight_booking.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Flight entity'si için repository interface'i.
 * JpaRepository'nin sağladığı CRUD işlemleri: save, delete, findById, findAll
 * vb.
 * İhtiyaca göre özel query'ler eklenebilir.
 */
@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

}
