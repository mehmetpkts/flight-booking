package com.example.flight_booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import com.example.flight_booking.enums.BookingStatus;

@Entity
public class Aircraft {
  @Id // PK değerimiz
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Otomatik olarak ID değeri üretilecek
  private Long aircraftId;

  @Column(nullable = false) // Boş olamaz
  private String model;

  @Column(nullable = false) // Boş olamaz
  private String manufacturer;

  @Column(nullable = false) // Boş olamaz
  private int capacity;

  @Column(nullable = false) // Boş olamaz ve benzersiz olmalı
  @Enumerated(EnumType.STRING) // Enum değerini String olarak saklamak için
  private BookingStatus status;

  // birden fazla aircraft bir airline'e ait olabilir. Bu yüzden ManyToOne
  // ilişkisi kuruyoruz.
  @ManyToOne
  @JoinColumn(name = "airline_id", nullable = false) // Boş olamaz
  private Airline airline;

  // getter ve setter metodları

  // id
  public Long getAircraftId() {
    return aircraftId;
  }

  public void setAircraftId(Long aircraftId) {
    this.aircraftId = aircraftId;
  }

  // model
  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  // manufacturer
  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  // capacity
  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  // status
  public BookingStatus getStatus() {
    return status;
  }

  public void setStatus(BookingStatus status) {
    this.status = status;
  }

  // airline
  public Airline getAirline() {
    return airline;
  }

  public void setAirline(Airline airline) {
    this.airline = airline;
  }
}
