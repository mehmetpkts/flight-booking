package com.example.flight_booking.mapper;

import com.example.flight_booking.dto.flight.FlightCreateRequestDto;
import com.example.flight_booking.dto.flight.FlightFilterResponseDto;
import com.example.flight_booking.dto.flight.FlightUpdateRequestDto;
import com.example.flight_booking.entity.Aircraft;
import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.entity.Airport;
import com.example.flight_booking.entity.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FlightMapper {

  @Mapping(target = "flightNumber", source = "createRequest.flightNumber")
  @Mapping(target = "departureAirport", source = "departureAirport")
  @Mapping(target = "arrivalAirport", source = "arrivalAirport")
  @Mapping(target = "aircraft", source = "aircraft")
  @Mapping(target = "airline", source = "airline")
  @Mapping(target = "departureTime", source = "createRequest.departureTime")
  @Mapping(target = "arrivalTime", source = "createRequest.arrivalTime")
  @Mapping(target = "status", source = "createRequest.status")
  Flight toEntity(
      FlightCreateRequestDto createRequest,
      Airport departureAirport,
      Airport arrivalAirport,
      Aircraft aircraft,
      Airline airline);

  @Mapping(target = "departureAirportName", source = "departureAirport.name")
  @Mapping(target = "departureAirportCity", source = "departureAirport.city")
  @Mapping(target = "arrivalAirportName", source = "arrivalAirport.name")
  @Mapping(target = "arrivalAirportCity", source = "arrivalAirport.city")
  @Mapping(target = "airlineName", source = "airline.name")
  @Mapping(target = "airlineCountry", source = "airline.country")
  FlightFilterResponseDto toFilterResponseDto(Flight flight);

  @Mapping(target = "flightNumber", source = "updateRequest.flightNumber")
  @Mapping(target = "departureAirport", source = "departureAirport")
  @Mapping(target = "arrivalAirport", source = "arrivalAirport")
  @Mapping(target = "aircraft", source = "aircraft")
  @Mapping(target = "airline", source = "airline")
  @Mapping(target = "departureTime", source = "updateRequest.departureTime")
  @Mapping(target = "arrivalTime", source = "updateRequest.arrivalTime")
  @Mapping(target = "status", source = "updateRequest.status")
  void updateEntity(
      @MappingTarget Flight flight,
      FlightUpdateRequestDto updateRequest,
      Airport departureAirport,
      Airport arrivalAirport,
      Aircraft aircraft,
      Airline airline);
}
