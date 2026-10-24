package com.example.flight_booking.mapper;

import com.example.flight_booking.dto.Ticket.TicketCreateRequestDto;
import com.example.flight_booking.dto.Ticket.TicketFilterResponseDto;
import com.example.flight_booking.dto.Ticket.TicketUpdateRequestDto;
import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TicketMapper {

  @Mapping(target = "bookingId", source = "booking.bookingId")
  TicketFilterResponseDto toFilterResponseDto(Ticket ticket);

  @Mapping(target = "booking", source = "booking")
  @Mapping(target = "ticketNumber", source = "createRequest.ticketNumber")
  @Mapping(target = "issueDate", source = "createRequest.issueDate")
  @Mapping(target = "price", source = "createRequest.price")
  Ticket toEntity(TicketCreateRequestDto createRequest, Booking booking);

  @Mapping(target = "price", source = "updateRequest.price")
  void updateEntity(@MappingTarget Ticket ticket, TicketUpdateRequestDto updateRequest);
}
