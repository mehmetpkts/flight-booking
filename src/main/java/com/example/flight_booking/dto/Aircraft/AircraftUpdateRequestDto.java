package com.example.flight_booking.dto.Aircraft;

import com.example.flight_booking.enums.AircraftStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AircraftUpdateRequestDto {
    @NotNull(message = "airline id must not be null")
    private Long airlineId;
    @NotNull(message = "Status must not be null")
    private AircraftStatus status;
}
