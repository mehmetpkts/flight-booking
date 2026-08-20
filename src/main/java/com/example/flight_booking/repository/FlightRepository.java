package com.example.flight_booking.repository;

import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.enums.FlightStatus;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

  //@Query("SELECT f FROM Flight f WHERE f.departureAirport.iataCode = :iataCode")
  List<Flight> findByDepartureAirportIataCode(String iataCode);

  List<Flight> findByDepartureAirport_cityAndArrivalAirport_cityAndStatus(String departureCity,
      String arrivalCity, FlightStatus flightStatus);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<Flight> findByFlightId(Long flightId);

}
