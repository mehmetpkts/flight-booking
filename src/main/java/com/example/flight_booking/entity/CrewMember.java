package com.example.flight_booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CrewMember {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long crewMemberId;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String role;

  @Column(nullable = false, unique = true)
  private int employeeNumber;

  @Column(nullable = false)
  private String phone;

  @ManyToOne
  @JoinColumn(name = "airline_id", nullable = false)
  private Airline airline;

  // getter ve setter metodları

  // id
  public Long getCrewMemberId() {
    return crewMemberId;
  }

  public void setCrewMemberId(Long crewMemberId) {
    this.crewMemberId = crewMemberId;
  }

  // name
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  // lastname

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  // role
  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  // employeeNumber
  public int getEmployeeNumber() {
    return employeeNumber;
  }

  public void setEmployeeNumber(int employeeNumber) {
    this.employeeNumber = employeeNumber;
  }

  // phone

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  // airlineid
  public Airline getAirline() {
    return airline;
  }

  public void setAirline(Airline airline) {
    this.airline = airline;
  }
}
