package com.example.flight_booking.service;

import com.example.flight_booking.dto.Ticket.TicketCreateRequestDto;
import com.example.flight_booking.dto.Ticket.TicketFilterResponseDto;
import com.example.flight_booking.dto.Ticket.TicketUpdateRequestDto;
import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Ticket;
import com.example.flight_booking.mapper.TicketMapper;
import com.example.flight_booking.repository.TicketRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TicketService {

  private final TicketRepository ticketRepository;
  private final BookingService bookingService;
  private final TicketMapper ticketMapper;

  public TicketService(
      TicketRepository ticketRepository,
      BookingService bookingService,
      TicketMapper ticketMapper) {
    this.ticketRepository = ticketRepository;
    this.bookingService = bookingService;
    this.ticketMapper = ticketMapper;
  }

  private Ticket getTicketEntityById(Long id) {
    return ticketRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Ticket not found with id " + id));
  }

  private Booking getBookingEntityById(Long id) {
    return bookingService.getBookingEntityById(id);
  }


  public TicketFilterResponseDto getTicketById(Long id) {
    Ticket ticket = getTicketEntityById(id);
    return ticketMapper.toFilterResponseDto(ticket);
  }

  public Ticket createTicket(TicketCreateRequestDto create) {
    Booking booking = getBookingEntityById(create.getBookingId());
    Ticket ticket = ticketMapper.toEntity(create, booking);
    return ticketRepository.save(ticket);
  }

  public Ticket updateTicket(Long id, TicketUpdateRequestDto update) {
    Ticket ticket = getTicketEntityById(id);
    Booking booking = getBookingEntityById(update.getBookingId());
    ticketMapper.updateEntity(ticket, update, booking);
    return ticketRepository.save(ticket);
  }

  public void deleteTicket(Long id) {
    ticketRepository.delete(getTicketEntityById(id));
  }

}
