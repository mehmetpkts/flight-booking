package com.example.flight_booking.controller;

import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Ticket;
import com.example.flight_booking.service.TicketService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

  private final TicketService ticketService;

  public TicketController(TicketService ticketService) {
    this.ticketService = ticketService;
  }

  @GetMapping
  public List<Ticket> getAllTickets() {
    return ticketService.getAllTickets();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
    return ResponseEntity.ok(ticketService.getTicketById(id));
  }

  record CreateTicketPayload(
      @NotNull(message = "Booking must not be null") Booking booking,
      @NotBlank(message = "Ticket number must not be blank") String ticketNumber,
      @NotNull(message = "Issue date must not be null") LocalDateTime issueDate,
      @NotNull(message = "Price must not be null") BigDecimal price) {
  }

  @PostMapping
  public ResponseEntity<Ticket> createTicket(@Valid @RequestBody CreateTicketPayload payload) {
    Ticket createdTicket = ticketService.createTicket(
        payload.booking(),
        payload.ticketNumber(),
        payload.issueDate(),
        payload.price());
    return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Ticket> updateTicket(@PathVariable Long id,
      @Valid @RequestBody CreateTicketPayload payload) {
    Ticket updatedTicket = ticketService.updateTicket(
        id,
        payload.booking(),
        payload.ticketNumber(),
        payload.issueDate(),
        payload.price());
    if (updatedTicket != null) {
      return ResponseEntity.ok(updatedTicket);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
    ticketService.deleteTicket(id);
    return ResponseEntity.noContent().build();
  }
}
