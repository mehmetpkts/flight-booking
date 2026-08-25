package com.example.flight_booking.mapper;

import com.example.flight_booking.dto.flight.FlightCreateRequestDto;
import com.example.flight_booking.dto.flight.FlightFilterResponseDto;
import com.example.flight_booking.dto.flight.FlightUpdateRequestDto;
import com.example.flight_booking.entity.Aircraft;
import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.entity.Airport;
import com.example.flight_booking.entity.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper {

  public Flight toEntity(
      FlightCreateRequestDto createRequest,
      Airport departureAirport,
      Airport arrivalAirport,
      Aircraft aircraft,
      Airline airline) {
    Flight flight = new Flight();
    flight.setFlightNumber(createRequest.getFlightNumber());
    flight.setDepartureAirport(departureAirport);
    flight.setArrivalAirport(arrivalAirport);
    flight.setAircraft(aircraft);
    flight.setAirline(airline);
    flight.setDepartureTime(createRequest.getDepartureTime());
    flight.setArrivalTime(createRequest.getArrivalTime());
    flight.setStatus(createRequest.getStatus());
    return flight;
  }

  public FlightFilterResponseDto toFilterResponseDto(Flight flight) {
    FlightFilterResponseDto dto = new FlightFilterResponseDto();
    dto.setFlightId(flight.getFlightId());
    dto.setFlightNumber(flight.getFlightNumber());
    dto.setDepartureAirportName(flight.getDepartureAirport().getName());
    dto.setDepartureAirportCity(flight.getDepartureAirport().getCity());
    dto.setArrivalAirportName(flight.getArrivalAirport().getName());
    dto.setArrivalAirportCity(flight.getArrivalAirport().getCity());
    dto.setAirlineName(flight.getAirline().getName());
    dto.setAirlineCountry(flight.getAirline().getCountry());
    return dto;
  }

  public void updateEntity(
      Flight flight,
      FlightUpdateRequestDto updateRequest,
      Airport departureAirport,
      Airport arrivalAirport,
      Aircraft aircraft,
      Airline airline) {
    flight.setFlightNumber(updateRequest.getFlightNumber());
    flight.setDepartureAirport(departureAirport);
    flight.setArrivalAirport(arrivalAirport);
    flight.setAircraft(aircraft);
    flight.setAirline(airline);
    flight.setDepartureTime(updateRequest.getDepartureTime());
    flight.setArrivalTime(updateRequest.getArrivalTime());
    flight.setStatus(updateRequest.getStatus());
  }
}
