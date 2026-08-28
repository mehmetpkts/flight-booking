package com.example.flight_booking.dto.CrewMember;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CrewMemberUpdateRequestDto {
    @NotBlank(message = "Role must not be blank")
    private String role;
    @NotNull(message = "Employee number must not be null")
    private Integer employeeNumber;
    @NotBlank(message = "Phone must not be blank")
    private String phone;
    @NotNull(message = "Airline ID must not be null")
    private Long airlineId;
}
