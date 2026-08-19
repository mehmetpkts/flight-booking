package com.example.flight_booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
