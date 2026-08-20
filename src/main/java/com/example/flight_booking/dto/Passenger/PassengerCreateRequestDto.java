package com.example.flight_booking.dto.Passenger;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PassengerCreateRequestDto {
    @NotBlank(message = "Passenger first name must not be blank")
    private String firstName;

    @NotBlank(message = "Passenger last name must not be blank")
    private String lastName;

    @NotBlank(message = "Passenger passport number must not be blank")
    private String passportNumber;

    @NotBlank(message = "Passenger email must not be blank")
    private String email;

    @NotBlank(message = "Passenger phone number must not be blank")
    private String phoneNumber;

    @NotBlank(message = "Passenger nationality must not be blank")
    private String nationality;
}
