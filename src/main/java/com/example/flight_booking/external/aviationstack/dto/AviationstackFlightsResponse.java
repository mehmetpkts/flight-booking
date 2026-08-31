package com.example.flight_booking.external.aviationstack.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AviationstackFlightsResponse {

  private Pagination pagination;
  private List<FlightData> data;

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Pagination {
    private Integer limit;
    private Integer offset;
    private Integer count;
    private Integer total;
  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class FlightData {
    @JsonProperty("flight_date")
    private String flightDate;
    @JsonProperty("flight_status")
    private String flightStatus;
    private Endpoint departure;
    private Endpoint arrival;
    private AirlineInfo airline;
    private FlightCodes flight;
  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Endpoint {
    private String airport;
    private String timezone;
    private String iata;
    private String icao;
    private String terminal;
    private String gate;
    private Integer delay;
    private String scheduled;
    private String estimated;
    private String actual;
  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class AirlineInfo {
    @JsonProperty("airline_name")
    private String name;
    @JsonProperty("iata_code")
    private String iata;
    @JsonProperty("icao_code")
    private String icao;
  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class FlightCodes {
    private String number;
    private String iata;
    private String icao;
  }
}
