package com.example.flight_booking.repository;

import com.example.flight_booking.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

  @Query("SELECT f FROM Flight f LEFT JOIN f.departureAirport d WHERE d.iataCode = :iataCode")
  List<Flight> findByDepartureAirport_iataCodeFlights(String iataCode);

}
