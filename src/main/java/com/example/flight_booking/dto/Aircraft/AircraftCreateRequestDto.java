package com.example.flight_booking.dto.Aircraft;

import com.example.flight_booking.enums.AircraftStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class AircraftCreateRequestDto{

    @NotNull(message = "ailrine id not be null")
    private Long airlineId;
    @NotBlank(message = "Model must not be blank")
    private String model;
    @NotBlank(message = "Manufacturer must not be blank")
    private String manufacturer;
    @NotNull(message = "Capacity must not be null") @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;
    @NotNull(message = "Status must not be null")
    private AircraftStatus status;

}
