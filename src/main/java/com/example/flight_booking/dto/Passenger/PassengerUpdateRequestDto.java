package com.example.flight_booking.dto.Passenger;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class PassengerUpdateRequestDto {

  @NotBlank(message = "Passenger email must not be blank")
  @Email(message = "Passenger email format is invalid")
  private String email;

  @NotBlank(message = "Passenger phone number must not be blank")
  private String phoneNumber;
}
