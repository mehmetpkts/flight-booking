package com.example.flight_booking.controller;

import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.entity.Passenger;
import com.example.flight_booking.enums.BookingStatus;
import com.example.flight_booking.service.BookingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
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
@RequestMapping("/api/bookings")
public class BookingController {

  private final BookingService bookingService;

  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @GetMapping
  public List<Booking> getAllBookings() {
    return bookingService.getAllBookings();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
    return ResponseEntity.ok(bookingService.getBookingById(id));
  }

  record CreateBookingPayload(
      @NotNull(message = "Passenger must not be null") Passenger passenger,
      @NotNull(message = "Flight must not be null") Flight flight,
      @NotNull(message = "Booking date must not be null") LocalDateTime bookingDate,
      @NotNull(message = "Booking status must not be null") BookingStatus status,
      @NotBlank(message = "PNR must not be blank") String pnr) {
  }

  @PostMapping
  public ResponseEntity<Booking> createBooking(@Valid @RequestBody CreateBookingPayload payload) {
    Booking savedBooking = bookingService.createBooking(
        payload.passenger(),
        payload.flight(),
        payload.bookingDate(),
        payload.status(),
        payload.pnr());
    return ResponseEntity.ok(savedBooking);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Booking> updateBooking(@PathVariable Long id,
      @Valid @RequestBody CreateBookingPayload payload) {
    Booking updatedBooking = bookingService.updateBooking(
        id,
        payload.passenger(),
        payload.flight(),
        payload.bookingDate(),
        payload.status(),
        payload.pnr());
    return ResponseEntity.ok(updatedBooking);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
    bookingService.deleteBooking(id);
    return ResponseEntity.noContent().build();
  }

}
