package com.example.flight_booking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import jakarta.validation.constraints.NotEmpty;
import com.example.flight_booking.entity.Booking;
import java.util.List;
import com.example.flight_booking.entity.Ticket;
import com.example.flight_booking.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/tickets")
public class TicketController {

  private final TicketService ticketService;

  public TicketController(TicketService ticketService) {
    this.ticketService = ticketService;
  }

  // curd işlemleri

  @GetMapping()
  public List<Ticket> getAllTickets() {
    return ticketService.getAllTickets();
  }

  // id ile okuma
  @GetMapping("/id")
  public Ticket getTicketById(@RequestParam Long id) {
    return ticketService.getTicketById(id);
  }

  // oluşturma kaydetme

  record CreateTicketPayload(@NotEmpty Booking bookingId,
      @NotEmpty String ticketNumber,
      @NotEmpty String issueDate, @NotEmpty Double price) {
  };

  @PostMapping()
  public ResponseEntity<Ticket> createTicket(@Valid @RequestBody CreateTicketPayload payload) {
    Ticket createdTicket = ticketService.createTicket(payload.bookingId(), payload.ticketNumber(),
        java.time.LocalDateTime.parse(payload.issueDate()), java.math.BigDecimal.valueOf(payload.price()));
    return ResponseEntity.ok(createdTicket);
  }

  // güncelleme

  @PutMapping("/{id}")
  public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @Valid @RequestBody CreateTicketPayload payload) {
    Ticket updatedTicket = ticketService.updateTicket(id, payload.bookingId(), payload.ticketNumber(),
        java.time.LocalDateTime.parse(payload.issueDate()), java.math.BigDecimal.valueOf(payload.price()));
    if (updatedTicket != null) {
      return ResponseEntity.ok(updatedTicket);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  // silme

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
    ticketService.deleteTicket(id);
    return ResponseEntity.noContent().build();
  }

}
