package com.example.flight_booking.repository;

import com.example.flight_booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Booking entity'si için repository interface'i.
 * Müşterinin uçuş rezervasyonlarını veritabanında yönetir.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
