package com.example.flight_booking.external.aviationstack.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class FlightData {

        @JsonProperty("flight_date")
        private String flightDate;

        @JsonProperty("flight_status")
        private String flightStatus;

        private Endpoint departure;
        private Endpoint arrival;
        private AirlineInfo airline;
        private FlightCodes flight;
    }
