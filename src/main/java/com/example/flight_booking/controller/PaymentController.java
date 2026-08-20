package com.example.flight_booking.controller;

import com.example.flight_booking.dto.Payment.PaymentCreateRequestDto;
import com.example.flight_booking.dto.Payment.PaymentFilterResponseDto;
import com.example.flight_booking.dto.Payment.PaymentUpdateRequestDto;
import com.example.flight_booking.entity.Payment;
import com.example.flight_booking.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

  @GetMapping("/{id}")
  public ResponseEntity<PaymentFilterResponseDto> getPaymentById(@PathVariable Long id) {
    return ResponseEntity.ok(paymentService.getPaymentById(id));
  }

  @PostMapping
  public ResponseEntity<Payment> createPayment(@Valid @RequestBody PaymentCreateRequestDto create) {
    Payment payment = paymentService.createPayment(create);
    return ResponseEntity.status(HttpStatus.CREATED).body(payment);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Payment> updatePayment(@PathVariable Long id,
      @Valid @RequestBody PaymentUpdateRequestDto update) {
    Payment updatedPayment = paymentService.updatePayment(id, update);
    return ResponseEntity.ok(updatedPayment);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
    paymentService.deletePayment(id);
    return ResponseEntity.noContent().build();
  }
}
