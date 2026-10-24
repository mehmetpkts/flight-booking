package com.example.flight_booking.mapper;

import com.example.flight_booking.dto.Payment.PaymentCreateRequestDto;
import com.example.flight_booking.dto.Payment.PaymentFilterResponseDto;
import com.example.flight_booking.dto.Payment.PaymentUpdateRequestDto;
import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

  @Mapping(target = "bookingId", source = "booking.bookingId")
  PaymentFilterResponseDto toFilterResponseDto(Payment payment);

  @Mapping(target = "booking", source = "booking")
  @Mapping(target = "amount", source = "createRequest.amount")
  @Mapping(target = "paymentMethod", source = "createRequest.paymentMethod")
  @Mapping(target = "paymentDate", source = "createRequest.paymentDate")
  @Mapping(target = "status", source = "createRequest.status")
  Payment toEntity(PaymentCreateRequestDto createRequest, Booking booking);

  @Mapping(target = "amount", source = "updateRequest.amount")
  @Mapping(target = "paymentMethod", source = "updateRequest.paymentMethod")
  @Mapping(target = "status", source = "updateRequest.status")
  void updateEntity(@MappingTarget Payment payment, PaymentUpdateRequestDto updateRequest);
}
