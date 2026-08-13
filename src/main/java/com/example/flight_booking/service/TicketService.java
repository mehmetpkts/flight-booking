package com.example.flight_booking.service;

import com.example.flight_booking.repository.TicketRepository;
import com.example.flight_booking.entity.Ticket;
import com.example.flight_booking.entity.Booking;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Service
public class TicketService {

  private final TicketRepository ticketRepository;

  public TicketService(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  // Oluşturma

  public Ticket createTicket(Booking booking, String ticketNumber, LocalDateTime issueDate, BigDecimal price) {
    Ticket ticket = new Ticket();
    ticket.setBooking(booking);
    ticket.setTicketNumber(ticketNumber);
    ticket.setIssueDate(issueDate);
    ticket.setPrice(price);
    return ticketRepository.save(ticket);
  }

  // Okuma

  public List<Ticket> getAllTickets() {
    return ticketRepository.findAll();
  }

  // id okuma

  public Ticket getTicketById(Long id) {
    return ticketRepository.findById(id).orElse(null);
  }

  // Güncelleme

  public Ticket updateTicket(Long id, Booking booking, String ticketNumber, LocalDateTime issueDate, BigDecimal price) {
    Ticket ticket = ticketRepository.findById(id).orElse(null);
    if (ticket != null) {
      ticket.setBooking(booking);
      ticket.setTicketNumber(ticketNumber);
      ticket.setIssueDate(issueDate);
      ticket.setPrice(price);
      return ticketRepository.save(ticket);
    }
    return null;
  }

  // Silme

  public void deleteTicket(Long id) {
    ticketRepository.deleteById(id);
  }

}
