package com.example.flight_booking.dto.CrewAssignment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CrewAssignmentUpdateRequestDto {
    @NotNull(message = "Flight ID must not be null")
    private Long flightId;
    @NotNull(message = "Crew member ID must not be null")
    private Long crewMemberId;
    @NotBlank(message = "Duty must not be blank")
    private String duty;
}
