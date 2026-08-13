package com.example.flight_booking.service;

import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Ticket;
import com.example.flight_booking.repository.TicketRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TicketService {

  private final TicketRepository ticketRepository;

  public TicketService(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  public Ticket createTicket(Booking booking, String ticketNumber, LocalDateTime issueDate, BigDecimal price) {
    Ticket ticket = new Ticket();
    ticket.setBooking(booking);
    ticket.setTicketNumber(ticketNumber);
    ticket.setIssueDate(issueDate);
    ticket.setPrice(price);
    return ticketRepository.save(ticket);
  }

  public List<Ticket> getAllTickets() {
    return ticketRepository.findAll();
  }

  public Ticket getTicketById(Long id) {
    return ticketRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Ticket not found with id " + id));
  }

  public Ticket updateTicket(Long id, Booking booking, String ticketNumber, LocalDateTime issueDate, BigDecimal price) {
    Ticket ticket = getTicketById(id);
    ticket.setBooking(booking);
    ticket.setTicketNumber(ticketNumber);
    ticket.setIssueDate(issueDate);
    ticket.setPrice(price);
    return ticketRepository.save(ticket);
  }

  public void deleteTicket(Long id) {
    Ticket ticket = getTicketById(id);
    ticketRepository.delete(ticket);
  }

}
