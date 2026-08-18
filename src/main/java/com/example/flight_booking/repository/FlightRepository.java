package com.example.flight_booking.repository;

import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.enums.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

  //@Query("SELECT f FROM Flight f WHERE f.departureAirport.iataCode = :iataCode")
  List<Flight> findByDepartureAirportIataCode(String iataCode);

  List<Flight> findByFlightNumber(String flightNumber);

  List<Flight> findByDepartureAirport_cityAndArrivalAirport_cityAndStatus(String departureCity,
      String arrivalCity, FlightStatus flightStatus);

  Optional<Flight> findByFlightId(Long flightId);

}
