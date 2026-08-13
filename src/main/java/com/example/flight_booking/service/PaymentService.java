package com.example.flight_booking.service;

import org.springframework.stereotype.Service;
import java.util.List;

import com.example.flight_booking.repository.PaymentRepository;
import com.example.flight_booking.entity.Payment;
import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.enums.PaymentStatus;
import java.time.LocalDateTime;

@Service
public class PaymentService {

  private final PaymentRepository paymentRepository;

  public PaymentService(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  // oluşturma kaydetme

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

  // okuma listeleme

  public List<Payment> getAllPayments() {
    return paymentRepository.findAll();
  }

  // id ile okuma

  public Payment getPaymentById(Long id) {
    return paymentRepository.findById(id).orElse(null);
  }

  // güncelleme

  public Payment updatePayment(Long id, Booking booking, Double amount, String paymentMethod,
      LocalDateTime paymentDate, PaymentStatus status) {
    Payment payment = paymentRepository.findById(id).orElse(null);
    if (payment != null) {
      payment.setBooking(booking);
      payment.setAmount(amount);
      payment.setPaymentMethod(paymentMethod);
      payment.setPaymentDate(paymentDate);
      payment.setStatus(status);
      return paymentRepository.save(payment);
    }
    return null;
  }

  // silme

  public void deletePayment(Long id) {
    paymentRepository.deleteById(id);
  }

}
