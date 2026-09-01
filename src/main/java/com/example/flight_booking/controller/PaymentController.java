package com.example.flight_booking.controller;

import com.example.flight_booking.dto.Payment.PaymentCreateRequestDto;
import com.example.flight_booking.dto.Payment.PaymentFilterResponseDto;
import com.example.flight_booking.dto.Payment.PaymentUpdateRequestDto;
import com.example.flight_booking.entity.Payment;
import com.example.flight_booking.service.PaymentService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
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
  private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

  @GetMapping("/{id}")
  public ResponseEntity<PaymentFilterResponseDto> getPaymentById(@PathVariable Long id) {

    logger.info("id'ye göre ödeme getirme isteği alındı.");
    PaymentFilterResponseDto payment = paymentService.getPaymentById(id);
    logger.info("id'ye göre ödeme isteği getirildi.");

    return ResponseEntity.ok(payment);
  }

  @PostMapping
  public ResponseEntity<Payment> createPayment(@Valid @RequestBody PaymentCreateRequestDto create) {

    logger.info("Ödeme oluşturma isteği alındı.");
    Payment payment = paymentService.createPayment(create);
    logger.info("Ödeme oluşturuldu!");

    return ResponseEntity.status(HttpStatus.CREATED).body(payment);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Payment> updatePayment(@PathVariable Long id,
      @Valid @RequestBody PaymentUpdateRequestDto update) {

    logger.info("id'ye göre ödeme tablosu güncelleme isteği alındı.");
    Payment updatedPayment = paymentService.updatePayment(id, update);
    logger.info("Ödeme veirisinde güncelleme tamamlandı.");

    return ResponseEntity.ok(updatedPayment);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePayment(@PathVariable Long id) {

    logger.info("Veri silme isteği alındı.");
    paymentService.deletePayment(id);
    logger.info("Veri silindi!");

    return ResponseEntity.noContent().build();
  }
}
