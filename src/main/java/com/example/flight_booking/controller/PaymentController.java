package com.example.flight_booking.controller;

import com.example.flight_booking.entity.Payment;
import com.example.flight_booking.enums.PaymentStatus;
import com.example.flight_booking.service.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @GetMapping
  public List<Payment> getAllPayments() {
    return paymentService.getAllPayments();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
    return ResponseEntity.ok(paymentService.getPaymentById(id));
  }

  record CreatePaymentPayload(
      @NotNull(message = "Booking ID must not be null") Long bookingId,
      @NotNull(message = "Amount must not be null") Double amount,
      @NotBlank(message = "Payment method must not be blank") String paymentMethod,
      @NotNull(message = "Payment date must not be null") LocalDateTime paymentDate,
      @NotNull(message = "Status must not be null") PaymentStatus status) {
  }

  @PostMapping
  public ResponseEntity<Payment> createPayment(@Valid @RequestBody CreatePaymentPayload payload) {
    Payment payment = paymentService.createPayment(
        payload.bookingId(),
        payload.amount(),
        payload.paymentMethod(),
        payload.paymentDate(),
        payload.status());
    return ResponseEntity.ok(payment);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Payment> updatePayment(@PathVariable Long id,
      @Valid @RequestBody CreatePaymentPayload payload) {
    Payment updatedPayment = paymentService.updatePayment(
        id,
        payload.bookingId(),
        payload.amount(),
        payload.paymentMethod(),
        payload.paymentDate(),
        payload.status());
    return ResponseEntity.ok(updatedPayment);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
    paymentService.deletePayment(id);
    return ResponseEntity.noContent().build();
  }
}
