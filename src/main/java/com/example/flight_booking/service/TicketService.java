package com.example.flight_booking.service;

import com.example.flight_booking.dto.Ticket.TicketCreateRequestDto;
import com.example.flight_booking.dto.Ticket.TicketFilterResponseDto;
import com.example.flight_booking.dto.Ticket.TicketUpdateRequestDto;
import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Ticket;
import com.example.flight_booking.enums.BookingStatus;
import com.example.flight_booking.mapper.TicketMapper;
import com.example.flight_booking.repository.TicketRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TicketService {

  private final TicketRepository ticketRepository;
  private final BookingService bookingService;
  private final PaymentService paymentService;
  private final TicketMapper ticketMapper;

  public TicketService(
      TicketRepository ticketRepository,
      BookingService bookingService,
      PaymentService paymentService,
      TicketMapper ticketMapper) {
    this.ticketRepository = ticketRepository;
    this.bookingService = bookingService;
    this.paymentService = paymentService;
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

  private void validateBookingEligibilityForTicket(Booking booking) {
    if (booking.getStatus() == BookingStatus.CANCELLED) {
      throw new ResponseStatusException(HttpStatus.CONFLICT,
          "Ticket cannot be issued for cancelled booking id " + booking.getBookingId());
    }

    if (ticketRepository.existsByBooking_BookingId(booking.getBookingId())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT,
          "Ticket already issued for booking id " + booking.getBookingId());
    }

    if (!paymentService.hasCompletedPaymentForBooking(booking.getBookingId())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT,
          "Ticket cannot be issued before completed payment for booking id "
              + booking.getBookingId());
    }
  }


  public TicketFilterResponseDto getTicketById(Long id) {
    Ticket ticket = getTicketEntityById(id);
    return ticketMapper.toFilterResponseDto(ticket);
  }

  public Ticket createTicket(TicketCreateRequestDto create) {
    Booking booking = getBookingEntityById(create.getBookingId());
    validateBookingEligibilityForTicket(booking);
    Ticket ticket = ticketMapper.toEntity(create, booking);
    return ticketRepository.save(ticket);
  }

  public Ticket updateTicket(Long id, TicketUpdateRequestDto update) {
    Ticket ticket = getTicketEntityById(id);
    ticketMapper.updateEntity(ticket, update);
    return ticketRepository.save(ticket);
  }

  public void deleteTicket(Long id) {
    ticketRepository.delete(getTicketEntityById(id));
  }

}
