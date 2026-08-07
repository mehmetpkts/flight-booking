package com.example.flight_booking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Payment {

  // payment id

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long paymentId;

  // Booking id

  @ManyToOne
  @JoinColumn(name = "booking_id", nullable = false)
  private Booking booking;

  // Amount
  @Column(nullable = false)
  private Double amount;

  // payment method

  @Column(nullable = false)
  private String paymentMethod;

  // payment date
  @Column(nullable = false)
  private LocalDateTime paymentDate;

  // status
  @Column(nullable = false)
  private String status;

  // getter and setter methods

  // id
  public Long getId() {
    return paymentId;
  }

  public void setId(Long paymentId) {
    this.paymentId = paymentId;
  }

  // booking

  public Booking getBooking() {
    return booking;
  }

  public void setBooking(Booking booking) {
    this.booking = booking;
  }

  // amount

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  // payment method

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  // payment date

  public LocalDateTime getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(LocalDateTime paymentDate) {
    this.paymentDate = paymentDate;
  }

  // status

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
