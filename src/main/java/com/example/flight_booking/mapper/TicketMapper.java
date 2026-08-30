package com.example.flight_booking.mapper;

import com.example.flight_booking.dto.Ticket.TicketCreateRequestDto;
import com.example.flight_booking.dto.Ticket.TicketFilterResponseDto;
import com.example.flight_booking.dto.Ticket.TicketUpdateRequestDto;
import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

  public TicketFilterResponseDto toFilterResponseDto(Ticket ticket) {
    TicketFilterResponseDto dto = new TicketFilterResponseDto();
    dto.setTicketId(ticket.getTicketId());
    dto.setBookingId(ticket.getBooking().getBookingId());
    dto.setTicketNumber(ticket.getTicketNumber());
    dto.setIssueDate(ticket.getIssueDate());
    dto.setPrice(ticket.getPrice());
    return dto;
  }

  public Ticket toEntity(TicketCreateRequestDto createRequest, Booking booking) {
    Ticket ticket = new Ticket();
    ticket.setBooking(booking);
    ticket.setTicketNumber(createRequest.getTicketNumber());
    ticket.setIssueDate(createRequest.getIssueDate());
    ticket.setPrice(createRequest.getPrice());
    return ticket;
  }

  public void updateEntity(Ticket ticket, TicketUpdateRequestDto updateRequest) {
    ticket.setPrice(updateRequest.getPrice());
  }
}
