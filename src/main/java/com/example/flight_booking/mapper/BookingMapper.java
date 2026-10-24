package com.example.flight_booking.mapper;

import com.example.flight_booking.dto.Booking.BookingCreateRequestDto;
import com.example.flight_booking.dto.Booking.BookingFilterResponseDto;
import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.entity.Passenger;
import com.example.flight_booking.enums.BookingStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PassengerMapper.class, FlightMapper.class})
public interface BookingMapper {

  @Mapping(target = "flight", source = "booking.flight")
  @Mapping(target = "passenger", source = "booking.passenger")
  @Mapping(target = "status", source = "effectiveStatus")
  BookingFilterResponseDto toFilterResponseDto(Booking booking, BookingStatus effectiveStatus);

  @Mapping(target = "bookingDate", source = "createRequest.bookingDate")
  @Mapping(target = "status", source = "effectiveStatus")
  @Mapping(target = "pnr", source = "pnr")
  @Mapping(target = "passenger", source = "passenger")
  @Mapping(target = "flight", source = "flight")
  Booking toEntity(
      BookingCreateRequestDto createRequest,
      Passenger passenger,
      Flight flight,
      BookingStatus effectiveStatus,
      String pnr);
}
