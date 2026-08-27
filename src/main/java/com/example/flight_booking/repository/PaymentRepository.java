package com.example.flight_booking.repository;

import com.example.flight_booking.entity.Payment;
import com.example.flight_booking.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

  boolean existsByBooking_BookingIdAndStatus(Long bookingId, PaymentStatus status);

}
