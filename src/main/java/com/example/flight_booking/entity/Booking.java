package com.example.flight_booking.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Booking {

  @Id // PK değerimiz
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Otomatik olarak ID değeri üretilecek
  private Long bookingId;

  // passengerId değerimiz

  @ManyToOne
  @JoinColumn(name = "passenger_id", nullable = false) // Boş olamaz
  private Passenger passenger;

  // flightId değerimiz

  @ManyToOne
  @JoinColumn(name = "flight_id", nullable = false) // Boş olamaz
  private Flight flight;

  // bookingDate değerimiz

  @Column(nullable = false) // Boş olamaz
  private LocalDateTime bookingDate;

  // status

  @Column(nullable = false) // Boş olamaz
  private String status;

  // PNR değerimiz
  @Column(nullable = false, unique = true, length = 6) // Boş olamaz ve benzersiz olmalı
  private String pnr;

  // getter setter metodları

  // id

  public Long getBookingId() {
    return bookingId;
  }

  public void setBookingId(Long bookingId) {
    this.bookingId = bookingId;
  }

  // passenger

  public Passenger getPassenger() {
    return passenger;
  }

  public void setPassenger(Passenger passenger) {
    this.passenger = passenger;
  }

  // flight

  public Flight getFlight() {
    return flight;
  }

  public void setFlight(Flight flight) {
    this.flight = flight;
  }

  // bookingDate

  public LocalDateTime getBookingDate() {
    return bookingDate;
  }

  public void setBookingDate(LocalDateTime bookingDate) {
    this.bookingDate = bookingDate;
  }

  // status

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  // pnr

  public String getPnr() {
    return pnr;
  }

  public void setPnr(String pnr) {
    this.pnr = pnr;
  }

}
