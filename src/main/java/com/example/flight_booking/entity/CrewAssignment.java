package com.example.flight_booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CrewAssignment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long assignmentId;

  @ManyToOne
  @JoinColumn(name = "flight_id", nullable = false)
  private Flight flight;

  @ManyToOne
  @JoinColumn(name = "crew_member_id", nullable = false)
  private CrewMember crewMember;

  @Column(nullable = false)
  private String duty;

  public Long getAssignmentId() {
    return assignmentId;
  }

  public void setAssignmentId(Long assignmentId) {
    this.assignmentId = assignmentId;
  }

  public Flight getFlight() {
    return flight;
  }

  public void setFlight(Flight flight) {
    this.flight = flight;
  }

  public CrewMember getCrewMember() {
    return crewMember;
  }

  public void setCrewMember(CrewMember crewMember) {
    this.crewMember = crewMember;
  }

  public String getDuty() {
    return duty;
  }

  public void setDuty(String duty) {
    this.duty = duty;
  }
}
