package com.example.flight_booking.repository;

import com.example.flight_booking.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Payment entity'si için repository interface'i.
 * Ödeme bilgilerini veritabanında yönetir.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
