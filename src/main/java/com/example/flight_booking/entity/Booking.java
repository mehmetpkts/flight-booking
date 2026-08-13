package com.example.flight_booking.entity;

import com.example.flight_booking.enums.BookingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bookingId;

  @ManyToOne
  @JoinColumn(name = "passenger_id", nullable = false)
  private Passenger passenger;

  @ManyToOne
  @JoinColumn(name = "flight_id", nullable = false)
  private Flight flight;

  @Column(nullable = false)
  private LocalDateTime bookingDate;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private BookingStatus status;

  @Column(nullable = false, unique = true, length = 6)
  private String pnr;

  public Long getBookingId() {
    return bookingId;
  }

  public void setBookingId(Long bookingId) {
    this.bookingId = bookingId;
  }

  public Passenger getPassenger() {
    return passenger;
  }

  public void setPassenger(Passenger passenger) {
    this.passenger = passenger;
  }

  public Flight getFlight() {
    return flight;
  }

  public void setFlight(Flight flight) {
    this.flight = flight;
  }

  public LocalDateTime getBookingDate() {
    return bookingDate;
  }

  public void setBookingDate(LocalDateTime bookingDate) {
    this.bookingDate = bookingDate;
  }

  public BookingStatus getStatus() {
    return status;
  }

  public void setStatus(BookingStatus status) {
    this.status = status;
  }

  public String getPnr() {
    return pnr;
  }

  public void setPnr(String pnr) {
    this.pnr = pnr;
  }
}
