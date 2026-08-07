package com.example.flight_booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Airport {

  @Id // PK değerimiz
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Otomatik olarak ID değeri üretilecek
  private Long airportId;

  // isim değerimiz
  @Column(nullable = false) // Boş olamaz
  private String name;

  // city değerimiz
  @Column(nullable = false) // Boş olamaz
  private String city;

  // Country değerimiz
  @Column(nullable = false) // Boş olamaz
  private String country;

  // IATA code değerimiz
  @Column(nullable = false, length = 3, unique = true) // Boş olamaz - 3 harften oluşmalı - benzersiz olmalı
  private String iataCode;

  // yine aynı şekilde bunlara dışardan erişebilmek için getter ve setter
  // metodları oluşturulması gerekiyor.

  // id
  public Long getAirportId() {
    return airportId;
  }

  public void setAirportId(Long airportId) {
    this.airportId = airportId;
  }

  // name
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  // city
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  // country
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  // iataCode
  public String getIataCode() {
    return iataCode;
  }

  public void setIataCode(String iataCode) {
    this.iataCode = iataCode;
  }
}
