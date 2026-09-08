package com.example.flight_booking.external.aviationstack.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightCodes {
    private String number;
    private String iata;
    private String icao;
}
