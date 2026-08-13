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

@Entity
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

  public Long getAircraftId() {
    return aircraftId;
  }

  public void setAircraftId(Long aircraftId) {
    this.aircraftId = aircraftId;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public AircraftStatus getStatus() {
    return status;
  }

  public void setStatus(AircraftStatus status) {
    this.status = status;
  }

  public Airline getAirline() {
    return airline;
  }

  public void setAirline(Airline airline) {
    this.airline = airline;
  }
}
