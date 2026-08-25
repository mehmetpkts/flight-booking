package com.example.flight_booking.service;

import com.example.flight_booking.dto.Payment.PaymentCreateRequestDto;
import com.example.flight_booking.dto.Payment.PaymentFilterResponseDto;
import com.example.flight_booking.dto.Payment.PaymentUpdateRequestDto;
import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Payment;
import com.example.flight_booking.mapper.PaymentMapper;
import com.example.flight_booking.repository.PaymentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PaymentService {

  private final PaymentRepository paymentRepository;
  private final BookingService bookingService;
  private final PaymentMapper paymentMapper;

  public PaymentService(
      PaymentRepository paymentRepository,
      BookingService bookingService,
      PaymentMapper paymentMapper) {
    this.paymentRepository = paymentRepository;
    this.bookingService = bookingService;
    this.paymentMapper = paymentMapper;
  }

  private Payment getPaymentEntityById(Long id) {
    return paymentRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Payment not found with id " + id));
  }

  private Booking getBookingEntityById(Long id) {
    return bookingService.getBookingEntityById(id);
  }


  public PaymentFilterResponseDto getPaymentById(Long id) {
    Payment payment = getPaymentEntityById(id);
    return paymentMapper.toFilterResponseDto(payment);
  }

  public Payment createPayment(PaymentCreateRequestDto create) {
    Booking booking = getBookingEntityById(create.getBookingId());
    Payment payment = paymentMapper.toEntity(create, booking);
    return paymentRepository.save(payment);
  }

  public Payment updatePayment(Long id, PaymentUpdateRequestDto update) {
    Payment payment = getPaymentEntityById(id);
    Booking booking = getBookingEntityById(update.getBookingId());
    paymentMapper.updateEntity(payment, update, booking);
    return paymentRepository.save(payment);
  }

  public void deletePayment(Long id) {
    paymentRepository.delete(getPaymentEntityById(id));
  }

}
