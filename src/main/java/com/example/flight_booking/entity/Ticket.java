package com.example.flight_booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Ticket {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long ticketId;

  @OneToOne
  @JoinColumn(name = "booking_id", nullable = false)
  private Booking booking;

  @Column(nullable = false, unique = true)
  private String ticketNumber;

  @Column(nullable = false)
  private LocalDateTime issueDate;

  @Column(nullable = false)
  private BigDecimal price;
}
