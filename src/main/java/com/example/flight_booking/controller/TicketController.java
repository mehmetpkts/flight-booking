package com.example.flight_booking.controller;

import com.example.flight_booking.dto.Ticket.TicketCreateRequestDto;
import com.example.flight_booking.dto.Ticket.TicketFilterResponseDto;
import com.example.flight_booking.dto.Ticket.TicketUpdateRequestDto;
import com.example.flight_booking.entity.Ticket;
import com.example.flight_booking.service.TicketService;
import jakarta.validation.Valid;
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

  @GetMapping("/{id}")
  public ResponseEntity<TicketFilterResponseDto> getTicketById(@PathVariable Long id) {
    return ResponseEntity.ok(ticketService.getTicketById(id));
  }

  @PostMapping
  public ResponseEntity<Ticket> createTicket(@Valid @RequestBody TicketCreateRequestDto create) {
    Ticket createdTicket = ticketService.createTicket(create);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Ticket> updateTicket(@PathVariable Long id,
      @Valid @RequestBody TicketUpdateRequestDto update) {
    Ticket updatedTicket = ticketService.updateTicket(id, update);
    return ResponseEntity.ok(updatedTicket);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
    ticketService.deleteTicket(id);
    return ResponseEntity.noContent().build();
  }
}
