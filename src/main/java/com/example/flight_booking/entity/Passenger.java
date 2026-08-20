package com.example.flight_booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Passenger {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long passengerId;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false, unique = true)
  private String passportNumber;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String phone;

  @Column(nullable = false)
  private String nationality;
}
