package com.example.flight_booking.service;

import com.example.flight_booking.dto.Payment.PaymentCreateRequestDto;
import com.example.flight_booking.dto.Payment.PaymentFilterResponseDto;
import com.example.flight_booking.dto.Payment.PaymentUpdateRequestDto;
import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Payment;
import com.example.flight_booking.repository.BookingRepository;
import com.example.flight_booking.repository.PaymentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PaymentService {

  private final PaymentRepository paymentRepository;
  private final BookingRepository bookingRepository;

  public PaymentService(PaymentRepository paymentRepository, BookingRepository bookingRepository) {
    this.paymentRepository = paymentRepository;
    this.bookingRepository = bookingRepository;
  }

  private Booking getBookingEntityById(Long id) {
    return bookingRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Booking not found with id " + id));
  }

  private Payment getPaymentEntityById(Long id) {
    return paymentRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Payment not found with id " + id));
  }

  public PaymentFilterResponseDto getPaymentById(Long id) {
    Payment payment = getPaymentEntityById(id);

    PaymentFilterResponseDto dto = new PaymentFilterResponseDto();
    dto.setPaymentId(payment.getPaymentId());
    dto.setBookingId(payment.getBooking().getBookingId());
    dto.setAmount(payment.getAmount());
    dto.setPaymentMethod(payment.getPaymentMethod());
    dto.setPaymentDate(payment.getPaymentDate());
    dto.setStatus(payment.getStatus());

    return dto;
  }

  public Payment createPayment(PaymentCreateRequestDto create) {
    Booking booking = getBookingEntityById(create.getBookingId());
    Payment payment = new Payment();
    payment.setBooking(booking);
    payment.setAmount(create.getAmount());
    payment.setPaymentMethod(create.getPaymentMethod());
    payment.setPaymentDate(create.getPaymentDate());
    payment.setStatus(create.getStatus());
    return paymentRepository.save(payment);
  }

  public Payment updatePayment(Long id, PaymentUpdateRequestDto update) {
    Payment payment = getPaymentEntityById(id);
    Booking booking = getBookingEntityById(update.getBookingId());

    payment.setBooking(booking);
    payment.setAmount(update.getAmount());
    payment.setPaymentMethod(update.getPaymentMethod());
    payment.setPaymentDate(update.getPaymentDate());
    payment.setStatus(update.getStatus());
    return paymentRepository.save(payment);
  }

  public void deletePayment(Long id) {
    paymentRepository.delete(getPaymentEntityById(id));
  }

}
