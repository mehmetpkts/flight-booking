package com.example.flight_booking.controller;

import com.example.flight_booking.dto.Ticket.TicketCreateRequestDto;
import com.example.flight_booking.dto.Ticket.TicketFilterResponseDto;
import com.example.flight_booking.dto.Ticket.TicketUpdateRequestDto;
import com.example.flight_booking.entity.Ticket;
import com.example.flight_booking.service.TicketService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

  @GetMapping("/{id}")
  public ResponseEntity<TicketFilterResponseDto> getTicketById(@PathVariable Long id) {

    logger.info("id'ye göre bilet getirme isteği alındı.");
    TicketFilterResponseDto ticket = ticketService.getTicketById(id);
    logger.info("Bilet verisi alındı");

    return ResponseEntity.ok(ticket);
  }

  @PostMapping
  public ResponseEntity<Ticket> createTicket(@Valid @RequestBody TicketCreateRequestDto create) {

    logger.info("Bilet oluşturma isteği alındı.");
    Ticket createdTicket = ticketService.createTicket(create);
    logger.info("Bilet oluşturuldu.");

    return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Ticket> updateTicket(@PathVariable Long id,
      @Valid @RequestBody TicketUpdateRequestDto update) {

    logger.info("Bilet özellikleri güncelleme isteği alındı.");
    Ticket updatedTicket = ticketService.updateTicket(id, update);
    logger.info("Bilet özellikleri güncellendi!");

    return ResponseEntity.ok(updatedTicket);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
    logger.info("Bilet silem isteği alındı.");
    ticketService.deleteTicket(id);
    logger.info("Bilet silindi!");
    return ResponseEntity.noContent().build();
  }
}
