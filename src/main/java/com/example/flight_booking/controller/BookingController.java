package com.example.flight_booking.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.flight_booking.service.BookingService;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import com.example.flight_booking.enums.BookingStatus;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.entity.Passenger;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/bookings")
public class BookingController {

  private final BookingService bookingService;

  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  // crud işlemleri

  @GetMapping()
  public List<Booking> getAllBookings() {
    return bookingService.getAllBookings();
  }

  // Id ile okuma:

  @GetMapping("/{id}")
  public Booking getBookingById(@PathVariable Long id) {
    return bookingService.getBookingById(id);
  }

  // Oluşturma - Kaydetme:

  record CreateBookingPayload(
      @NotEmpty(message = "Passenger must not be empty") Passenger passengerId,
      @NotEmpty(message = "Flight must not be empty") Flight flightId,
      @NotEmpty(message = "Booking date must not be empty") LocalDateTime bookingDate,
      @NotEmpty(message = "Booking status must not be empty") BookingStatus status,
      @NotEmpty(message = "PNR must not be empty") String pnr) {
  };

  @PostMapping()
  public ResponseEntity<Booking> createBooking(@Valid @RequestBody CreateBookingPayload payload) {
    Booking savedBooking = bookingService.createBooking(payload.passengerId(), payload.flightId(),
        payload.bookingDate(),
        payload.status(), payload.pnr());
    return ResponseEntity.ok(savedBooking);
  }

  // Güncelleme:

  @PutMapping("/{id}")
  public ResponseEntity<Booking> updateBooking(@PathVariable Long id,
      @Valid @RequestBody CreateBookingPayload payload) {
    Booking updatedBooking = bookingService.updateBooking(id, payload.passengerId(), payload.flightId(),
        payload.bookingDate(),
        payload.status(), payload.pnr());
    return ResponseEntity.ok(updatedBooking);
  }

  // Silme

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
    bookingService.deleteBooking(id);
    return ResponseEntity.noContent().build();
  }

}
