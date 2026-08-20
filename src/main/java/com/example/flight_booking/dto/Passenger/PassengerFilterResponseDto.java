package com.example.flight_booking.dto.Passenger;

import lombok.Data;

@Data
public class PassengerFilterResponseDto {
  private String firstName;
  private String lastName;
  private String passportNumber;
  private String email;
  private String phoneNumber;
  private String nationality;
}
