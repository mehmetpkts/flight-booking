package com.example.flight_booking.service;

import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.entity.Passenger;
import com.example.flight_booking.enums.BookingStatus;
import com.example.flight_booking.repository.BookingRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookingService {

  private final BookingRepository bookingRepository;

  public BookingService(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
  }

  public Booking createBooking(Passenger passenger, Flight flight, LocalDateTime bookingDate, BookingStatus status,
      String pnr) {
    Booking booking = new Booking();
    booking.setPassenger(passenger);
    booking.setFlight(flight);
    booking.setBookingDate(bookingDate);
    booking.setStatus(status);
    booking.setPnr(pnr);
    return bookingRepository.save(booking);
  }

  public List<Booking> getAllBookings() {
    return bookingRepository.findAll();
  }

  public Booking getBookingById(Long id) {
    return bookingRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Booking not found with id " + id));
  }

  public Booking updateBooking(Long id, Passenger passenger, Flight flight, LocalDateTime bookingDate,
      BookingStatus status, String pnr) {
    Booking booking = getBookingById(id);
    booking.setPassenger(passenger);
    booking.setFlight(flight);
    booking.setBookingDate(bookingDate);
    booking.setStatus(status);
    booking.setPnr(pnr);
    return bookingRepository.save(booking);
  }

  public void deleteBooking(Long id) {
    Booking booking = getBookingById(id);
    bookingRepository.delete(booking);
  }

}
