package com.example.flight_booking.entity;

import com.example.flight_booking.enums.FlightStatus;
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
public class Flight {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long flightId;

  @Column(nullable = false, unique = true)
  private String flightNumber;

  @ManyToOne
  @JoinColumn(name = "departure_airport_id", nullable = false)
  private Airport departureAirport;

  @ManyToOne
  @JoinColumn(name = "arrival_airport_id", nullable = false)
  private Airport arrivalAirport;

  @ManyToOne
  @JoinColumn(name = "aircraft_id", nullable = false)
  private Aircraft aircraft;

  @ManyToOne
  @JoinColumn(name = "airline_id", nullable = false)
  private Airline airline;

  @Column(nullable = false)
  private LocalDateTime departureTime;

  @Column(nullable = false)
  private LocalDateTime arrivalTime;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private FlightStatus status;

  public Long getFlightId() {
    return flightId;
  }

  public void setFlightId(Long flightId) {
    this.flightId = flightId;
  }

  public String getFlightNumber() {
    return flightNumber;
  }

  public void setFlightNumber(String flightNumber) {
    this.flightNumber = flightNumber;
  }

  public Airport getDepartureAirport() {
    return departureAirport;
  }

  public void setDepartureAirport(Airport departureAirport) {
    this.departureAirport = departureAirport;
  }

  public Airport getArrivalAirport() {
    return arrivalAirport;
  }

  public void setArrivalAirport(Airport arrivalAirport) {
    this.arrivalAirport = arrivalAirport;
  }

  public Aircraft getAircraft() {
    return aircraft;
  }

  public void setAircraft(Aircraft aircraft) {
    this.aircraft = aircraft;
  }

  public Airline getAirline() {
    return airline;
  }

  public void setAirline(Airline airline) {
    this.airline = airline;
  }

  public LocalDateTime getDepartureTime() {
    return departureTime;
  }

  public void setDepartureTime(LocalDateTime departureTime) {
    this.departureTime = departureTime;
  }

  public LocalDateTime getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(LocalDateTime arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  public FlightStatus getStatus() {
    return status;
  }

  public void setStatus(FlightStatus status) {
    this.status = status;
  }
}
