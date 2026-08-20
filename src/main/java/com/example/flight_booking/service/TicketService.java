package com.example.flight_booking.service;

import com.example.flight_booking.dto.Ticket.TicketCreateRequestDto;
import com.example.flight_booking.dto.Ticket.TicketFilterResponseDto;
import com.example.flight_booking.dto.Ticket.TicketUpdateRequestDto;
import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Ticket;
import com.example.flight_booking.repository.BookingRepository;
import com.example.flight_booking.repository.TicketRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TicketService {

  private final TicketRepository ticketRepository;
  private final BookingRepository bookingRepository;

  public TicketService(TicketRepository ticketRepository, BookingRepository bookingRepository) {
    this.ticketRepository = ticketRepository;
    this.bookingRepository = bookingRepository;
  }

  private Booking getBookingEntityById(Long id) {
    return bookingRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Booking not found with id " + id));
  }

  private Ticket getTicketEntityById(Long id) {
    return ticketRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Ticket not found with id " + id));
  }

  public TicketFilterResponseDto getTicketById(Long id) {
    Ticket ticket = getTicketEntityById(id);

    TicketFilterResponseDto dto = new TicketFilterResponseDto();
    dto.setTicketId(ticket.getTicketId());
    dto.setBookingId(ticket.getBooking().getBookingId());
    dto.setTicketNumber(ticket.getTicketNumber());
    dto.setIssueDate(ticket.getIssueDate());
    dto.setPrice(ticket.getPrice());

    return dto;
  }

  public Ticket createTicket(TicketCreateRequestDto create) {
    Booking booking = getBookingEntityById(create.getBookingId());
    Ticket ticket = new Ticket();
    ticket.setBooking(booking);
    ticket.setTicketNumber(create.getTicketNumber());
    ticket.setIssueDate(create.getIssueDate());
    ticket.setPrice(create.getPrice());
    return ticketRepository.save(ticket);
  }

  public Ticket updateTicket(Long id, TicketUpdateRequestDto update) {
    Ticket ticket = getTicketEntityById(id);
    Booking booking = getBookingEntityById(update.getBookingId());

    ticket.setBooking(booking);
    ticket.setTicketNumber(update.getTicketNumber());
    ticket.setIssueDate(update.getIssueDate());
    ticket.setPrice(update.getPrice());
    return ticketRepository.save(ticket);
  }

  public void deleteTicket(Long id) {
    ticketRepository.delete(getTicketEntityById(id));
  }

}
