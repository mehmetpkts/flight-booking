package com.example.flight_booking.service;

import org.springframework.stereotype.Service;
import com.example.flight_booking.repository.BookingRepository;
import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Passenger;
import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.enums.BookingStatus;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

  private final BookingRepository bookingRepository;

  public BookingService(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
  }

  // Oluşturma - Kaydetme:

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

  // Okuma - Listeleme:

  public List<Booking> getAllBookings() {
    return bookingRepository.findAll();
  }

  // Id ile okuma:

  public Booking getBookingById(Long id) {
    return bookingRepository.findById(id).orElse(null);
  }

  // Güncelleme:

  public Booking updateBooking(Long id, Passenger passenger, Flight flight, LocalDateTime bookingDate,
      BookingStatus status, String pnr) {
    Booking booking = bookingRepository.findById(id).orElse(null);
    if (booking != null) {
      booking.setPassenger(passenger);
      booking.setFlight(flight);
      booking.setBookingDate(bookingDate);
      booking.setStatus(status);
      booking.setPnr(pnr);
      return bookingRepository.save(booking);
    }
    return null;
  }

  // Silme:

  public void deleteBooking(Long id) {
    bookingRepository.deleteById(id);
  }

}
