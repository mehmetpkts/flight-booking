package com.example.flight_booking.entity;

import com.example.flight_booking.enums.AircraftStatus;
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

@Entity
@Setter
@Getter
public class Aircraft {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long aircraftId;

  @Column(nullable = false)
  private String model;

  @Column(nullable = false)
  private String manufacturer;

  @Column(nullable = false)
  private int capacity;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private AircraftStatus status;

  @ManyToOne
  @JoinColumn(name = "airline_id", nullable = false)
  private Airline airline;

}
