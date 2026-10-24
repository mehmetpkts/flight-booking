package com.example.flight_booking.mapper;

import com.example.flight_booking.dto.Passenger.PassengerCreateRequestDto;
import com.example.flight_booking.dto.Passenger.PassengerFilterResponseDto;
import com.example.flight_booking.dto.Passenger.PassengerUpdateRequestDto;
import com.example.flight_booking.entity.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

  @Mapping(target = "phoneNumber", source = "phone")
  PassengerFilterResponseDto toFilterResponseDto(Passenger passenger);

  @Mapping(target = "phone", source = "createRequest.phoneNumber")
  Passenger toEntity(PassengerCreateRequestDto createRequest);

  @Mapping(target = "email", source = "updateRequest.email")
  @Mapping(target = "phone", source = "updateRequest.phoneNumber")
  void updateEntity(@MappingTarget Passenger passenger, PassengerUpdateRequestDto updateRequest);
}
