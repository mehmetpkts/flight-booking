package com.example.flight_booking.mapper;

import com.example.flight_booking.dto.Booking.BookingCreateRequestDto;
import com.example.flight_booking.dto.Booking.BookingFilterResponseDto;
import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.entity.Passenger;
import com.example.flight_booking.enums.BookingStatus;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

  public BookingFilterResponseDto toFilterResponseDto(Booking booking, BookingStatus effectiveStatus) {
    BookingFilterResponseDto dto = new BookingFilterResponseDto();
    dto.setFlight(booking.getFlight());
    dto.setBookingDate(booking.getBookingDate());
    dto.setPassenger(booking.getPassenger());
    dto.setPnr(booking.getPnr());
    dto.setStatus(effectiveStatus);
    dto.setCancellationPenaltyApplied(booking.getCancellationPenaltyApplied());
    dto.setCancellationPenaltyAmount(booking.getCancellationPenaltyAmount());
    return dto;
  }

  public Booking toEntity(
      BookingCreateRequestDto createRequest,
      Passenger passenger,
      Flight flight,
      BookingStatus effectiveStatus,
      String pnr) {
    Booking booking = new Booking();
    booking.setBookingDate(createRequest.getBookingDate());
    booking.setPnr(pnr);
    booking.setStatus(effectiveStatus);
    booking.setPassenger(passenger);
    booking.setFlight(flight);
    return booking;
  }
}
