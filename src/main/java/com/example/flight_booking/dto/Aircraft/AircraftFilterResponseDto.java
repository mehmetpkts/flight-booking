package com.example.flight_booking.dto.Aircraft;

import lombok.Data;

@Data
public class AircraftFilterResponseDto {
    private Long airlineId;
    private String airlineName;
    private String model;
    private String manufacturer;
    private Integer capacity;
    private String status;
}
