package com.example.flight_booking.repository;

import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.enums.BookingStatus;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

  long countByFlight_FlightIdAndStatusIn(Long flightId, Collection<BookingStatus> statuses);

}
