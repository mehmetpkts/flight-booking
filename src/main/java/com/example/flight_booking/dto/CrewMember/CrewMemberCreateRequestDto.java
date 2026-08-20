package com.example.flight_booking.dto.CrewMember;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CrewMemberCreateRequestDto {
    @NotBlank(message = "First name must not be blank")
    private String firstName;
    @NotBlank(message = "Last name must not be blank")
    private String lastName;
    @NotBlank(message = "Role must not be blank")
    private String role;
    @NotNull(message = "Employee number must not be null")
    private Integer employeeNumber;
    @NotBlank(message = "Phone must not be blank")
    private String phone;
    @NotNull(message = "Airline ID must not be null")
    private Long airlineId;
}
