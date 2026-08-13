package com.example.flight_booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Airline {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long airlineId;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(name = "iatacode", nullable = false, length = 3, unique = true)
  private String iataCode;

  @Column(nullable = false)
  private String country;

  public Long getAirlineId() {
    return airlineId;
  }

  public void setAirlineId(Long airlineId) {
    this.airlineId = airlineId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIataCode() {
    return iataCode;
  }

  public void setIataCode(String iataCode) {
    this.iataCode = iataCode;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }
}
