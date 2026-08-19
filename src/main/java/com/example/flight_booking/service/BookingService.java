package com.example.flight_booking.service;

import com.example.flight_booking.dto.Booking.BookingCreateRequestDto;
import com.example.flight_booking.dto.Booking.BookingFilterResponseDto;
import com.example.flight_booking.dto.Booking.BookingUpdateRequestDto;
import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.entity.Passenger;
import com.example.flight_booking.repository.BookingRepository;
import com.example.flight_booking.repository.FlightRepository;
import com.example.flight_booking.repository.PassengerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookingService {

  private final BookingRepository bookingRepository;
  private final PassengerRepository passengerRepository;
  private final FlightRepository flightRepository;

  public BookingService(BookingRepository bookingRepository,
      PassengerRepository passengerRepository,
      FlightRepository flightRepository) {
    this.bookingRepository = bookingRepository;
    this.passengerRepository = passengerRepository;
    this.flightRepository = flightRepository;
  }

  public Booking getBookingEntityById(Long id) {
    return bookingRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Booking not found with id " + id));
  }

  public BookingFilterResponseDto getBookingById(Long id){
    Booking booking = getBookingEntityById(id);

    BookingFilterResponseDto dto = new BookingFilterResponseDto();

    dto.setFlight(booking.getFlight());
    dto.setBookingDate(booking.getBookingDate());
    dto.setPassenger(booking.getPassenger());
    dto.setPnr(booking.getPnr());
    dto.setStatus(booking.getStatus());

    return dto;
  }


  public Booking createBooking(BookingCreateRequestDto create) {

    Booking booking = new Booking();
    Passenger passenger = passengerRepository.findById(create.getPassengerId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "passenger id is not found. id is " + create.getPassengerId()));
    Flight flight = flightRepository.findById(create.getFlightId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "flight id is not found. id is " + create.getFlightId()));

    booking.setBookingDate(create.getBookingDate());
    booking.setPnr(create.getPnr());
    booking.setStatus(create.getStatus());
    booking.setPassenger(passenger);
    booking.setFlight(flight);


    return bookingRepository.save(booking);
  }


  public Booking updateBooking(Long id, BookingUpdateRequestDto update) {
    Booking booking = getBookingEntityById(id);
    Passenger passenger = passengerRepository.findById(update.getPassengerId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "passenger id is not found. id is " + update.getPassengerId()));
    Flight flight = flightRepository.findById(update.getFlightId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "flight id is not found. id is " + update.getFlightId()));

    booking.setBookingDate(update.getBookingDate());
    booking.setPnr(update.getPnr());
    booking.setStatus(update.getStatus());
    booking.setPassenger(passenger);
    booking.setFlight(flight);


    return bookingRepository.save(booking);
  }

  public void deleteBooking(Long id) {
    Booking booking = getBookingEntityById(id);
    bookingRepository.delete(booking);
  }

}
