package com.example.flight_booking.service;

import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Payment;
import com.example.flight_booking.enums.PaymentStatus;
import com.example.flight_booking.repository.PaymentRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PaymentService {

  private final PaymentRepository paymentRepository;

  public PaymentService(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  public Payment createPayment(Booking booking, Double amount, String paymentMethod, LocalDateTime paymentDate,
      PaymentStatus status) {
    Payment payment = new Payment();
    payment.setBooking(booking);
    payment.setAmount(amount);
    payment.setPaymentMethod(paymentMethod);
    payment.setPaymentDate(paymentDate);
    payment.setStatus(status);
    return paymentRepository.save(payment);
  }

  public List<Payment> getAllPayments() {
    return paymentRepository.findAll();
  }

  public Payment getPaymentById(Long id) {
    return paymentRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Payment not found with id " + id));
  }

  public Payment updatePayment(Long id, Booking booking, Double amount, String paymentMethod,
      LocalDateTime paymentDate, PaymentStatus status) {
    Payment payment = getPaymentById(id);
    payment.setBooking(booking);
    payment.setAmount(amount);
    payment.setPaymentMethod(paymentMethod);
    payment.setPaymentDate(paymentDate);
    payment.setStatus(status);
    return paymentRepository.save(payment);
  }

  public void deletePayment(Long id) {
    Payment payment = getPaymentById(id);
    paymentRepository.delete(payment);
  }

}
