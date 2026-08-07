package com.example.flight_booking.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;

@Entity
public class Passenger {

  @Id // PK değerimiz
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Otomatik olarak ID değeri üretilecek
  private Long passengerId;

  @Column(nullable = false) // Boş olamaz
  private String firstName;

  @Column(nullable = false) // Boş olamaz
  private String lastName;

  @Column(nullable = false, unique = true) // Boş olamaz ve benzersiz olmalı
  private String passportNumber;

  @Column(nullable = false) // Boş olamaz
  private String email;

  @Column(nullable = false) // Boş olamaz
  private String phone;

  @Column(nullable = false) // Boş olamaz
  private String nationality;

  // getter ve setter metodları

  // id
  public Long getPassengerId() {
    return passengerId;
  }

  public void setPassengerId(Long passengerId) {
    this.passengerId = passengerId;
  }

  // firstName
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  // lastName
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  // passaportNumber
  public String getPassaportNumber() {
    return passportNumber;
  }

  public void setPassaportNumber(String passaportNumber) {
    this.passportNumber = passaportNumber;
  }

  // email
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  // phone
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  // nationality
  public String getNationality() {
    return nationality;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

}
