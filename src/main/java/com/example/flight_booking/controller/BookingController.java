package com.example.flight_booking.controller;

import com.example.flight_booking.dto.Booking.BookingCreateRequestDto;
import com.example.flight_booking.dto.Booking.BookingFilterResponseDto;
import com.example.flight_booking.dto.Booking.BookingUpdateRequestDto;
import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.service.BookingService;
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
@RequestMapping("/api/bookings")
public class BookingController {

  private final BookingService bookingService;

  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }
  private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

  @GetMapping("/{id}")
  public ResponseEntity<BookingFilterResponseDto> getBookingById(@PathVariable Long id) {

    logger.info("Id ile rezervasyon getirilme isteği oluşturuldu. bookingId = {}", id);
    BookingFilterResponseDto booking = bookingService.getBookingById(id);
    logger.info("Rezervasyon getirildi. bookingId = {}", id);

    return ResponseEntity.ok(booking);
  }

  @PostMapping
  public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingCreateRequestDto create) {

    logger.info("Rezervasyon oluşturma isteği alındı.");
    Booking savedBooking = bookingService.createBooking(create);
    logger.info("Rezervasyon oluşturuldu!");

    return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
  }

  @PutMapping("/{id}/status")
  public ResponseEntity<Booking> updateBookingStatus(@PathVariable Long id,
      @Valid @RequestBody BookingUpdateRequestDto update) {

    logger.info("Rezevasyon statüsünü güncelleme isteği alındı. BookingId: {}", id);
    Booking updatedBooking = bookingService.updateBookingStatus(id, update);
    logger.info("Rezervasyon statüsü güncellendi! BookingId: {}", id);
    return ResponseEntity.ok(updatedBooking);
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {

    logger.info("Rezervasyon silme isteği oluşturuldu. BookingId: {}",id);
    bookingService.deleteBooking(id);
    logger.info("Rezervasyon silindi. BookingId: {}", id);

    return ResponseEntity.noContent().build();
  }

}
