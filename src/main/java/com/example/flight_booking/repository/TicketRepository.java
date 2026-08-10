package com.example.flight_booking.repository;

import com.example.flight_booking.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Ticket entity'si için repository interface'i.
 * Bilet bilgilerini veritabanında yönetir.
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
