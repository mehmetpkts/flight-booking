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
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
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
}
