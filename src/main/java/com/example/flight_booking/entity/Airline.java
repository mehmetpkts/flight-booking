package com.example.flight_booking.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Airline {

  @Id // PK değerimiz
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Otomatik olarak ID değeri üretilecek
  private Long airlineId;

  @Column(nullable = false, unique = true) // Boş olamaz ve benzersiz olmalı
  private String name;

  @Column(name = "iatacode", nullable = false, length = 3, unique = true) // Boş olamaz - 3 harften oluşmalı - benzersiz
                                                                          // olmalı
  private String iataCode;

  @Column(nullable = false) // Boş olamaz
  private String country;

  // Bu fieldler için getter ve setter metodları oluşturulması gerekiyormuş
  // dışardan ulaşabilmek için.

  // id
  public Long getAirlineId() {
    return airlineId;
  }

  public void setAirlineId(Long airlineId) {
    this.airlineId = airlineId;
  }

  // name
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  // iataCode
  public String getIataCode() {
    return iataCode;
  }

  public void setIataCode(String iataCode) {
    this.iataCode = iataCode;
  }

  // country
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

}
