package com.example.flight_booking.repository;

import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.enums.BookingStatus;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

  long countByFlight_FlightIdAndStatusIn(Long flightId, Collection<BookingStatus> statuses);

  boolean existsByPassenger_PassengerIdAndFlight_FlightId(Long passengerId, Long flightId);

  boolean existsByPassenger_PassengerIdAndFlight_FlightIdAndBookingIdNot(Long passengerId,
      Long flightId, Long bookingId);

  boolean existsByPnr(String pnr);

  boolean existsByPnrAndBookingIdNot(String pnr, Long bookingId);

}
