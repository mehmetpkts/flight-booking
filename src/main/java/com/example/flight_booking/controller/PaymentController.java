package com.example.flight_booking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.example.flight_booking.service.PaymentService;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Payment;
import com.example.flight_booking.enums.PaymentStatus;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/payments")
public class PaymentController {

  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  // crud işlemleri

  @GetMapping()
  public List<Payment> getAllPayments() {
    return paymentService.getAllPayments();
  }

  // id ile okuma
  @GetMapping("/id")
  public Payment getPaymentById(@RequestParam Long id) {
    return paymentService.getPaymentById(id);
  }

  // oluşturma kaydetme

  record CreatePaymentPayload(@NotEmpty Booking bookingId,
      @NotEmpty Double amount,
      @NotEmpty String paymentMethod,
      @NotEmpty String paymentDate,
      @NotEmpty PaymentStatus status) {
  };

  @PostMapping()
  public ResponseEntity<Payment> createPayment(@Valid @RequestBody CreatePaymentPayload payload) {
    Payment payment = paymentService.createPayment(payload.bookingId(), payload.amount(),
        payload.paymentMethod(), java.time.LocalDateTime.parse(payload.paymentDate()), payload.status());
    return ResponseEntity.ok(payment);
  }

  // güncelleme

  @PutMapping("/{id}")
  public ResponseEntity<Payment> updatePayment(@PathVariable Long id,
      @Valid @RequestBody CreatePaymentPayload payload) {
    Payment updatedPayment = paymentService.updatePayment(id, payload.bookingId(), payload.amount(),
        payload.paymentMethod(), java.time.LocalDateTime.parse(payload.paymentDate()), payload.status());
    return ResponseEntity.ok(updatedPayment);
  }

  // silme

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
    paymentService.deletePayment(id);
    return ResponseEntity.noContent().build();
  }
}
