package com.example.flight_booking.mapper;

import com.example.flight_booking.dto.Payment.PaymentCreateRequestDto;
import com.example.flight_booking.dto.Payment.PaymentFilterResponseDto;
import com.example.flight_booking.dto.Payment.PaymentUpdateRequestDto;
import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

  public PaymentFilterResponseDto toFilterResponseDto(Payment payment) {
    PaymentFilterResponseDto dto = new PaymentFilterResponseDto();
    dto.setPaymentId(payment.getPaymentId());
    dto.setBookingId(payment.getBooking().getBookingId());
    dto.setAmount(payment.getAmount());
    dto.setPaymentMethod(payment.getPaymentMethod());
    dto.setPaymentDate(payment.getPaymentDate());
    dto.setStatus(payment.getStatus());
    return dto;
  }

  public Payment toEntity(PaymentCreateRequestDto createRequest, Booking booking) {
    Payment payment = new Payment();
    payment.setBooking(booking);
    payment.setAmount(createRequest.getAmount());
    payment.setPaymentMethod(createRequest.getPaymentMethod());
    payment.setPaymentDate(createRequest.getPaymentDate());
    payment.setStatus(createRequest.getStatus());
    return payment;
  }

  public void updateEntity(Payment payment, PaymentUpdateRequestDto updateRequest, Booking booking) {
    payment.setBooking(booking);
    payment.setAmount(updateRequest.getAmount());
    payment.setPaymentMethod(updateRequest.getPaymentMethod());
    payment.setPaymentDate(updateRequest.getPaymentDate());
    payment.setStatus(updateRequest.getStatus());
  }
}
