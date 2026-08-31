package com.example.flight_booking.external.aviationstack;

import com.example.flight_booking.external.aviationstack.dto.AviationstackFlightsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AviationstackService {

  private final RestClient restClient;
  private final String apiKey;

  public AviationstackService(RestClient.Builder restClientBuilder,
      @Value("${aviationstack.api.key}") String apiKey,
      @Value("${aviationstack.api.base-url}") String baseUrl) {
    this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    this.apiKey = apiKey;
  }

  // uçuş iata üzerinden çekme - tek bir uçuş üzerinden
  public AviationstackFlightsResponse getFlights(String flightIata) {
    return restClient.get()
        .uri(uriBuilder -> {
          var builder = uriBuilder.path("/flights")
              .queryParam("access_key", apiKey);
          if (flightIata != null && !flightIata.isBlank()) {
            builder.queryParam("flight_iata", flightIata);
          }
          return builder.build();
        })
        .retrieve()
        .body(AviationstackFlightsResponse.class);
  }

  // toplu çekme - yani ist yazınca bütün istanbula gelen giden uşuçlar
  public AviationstackFlightsResponse searchFlights(String departureIata, String arrivalIata) {
    return restClient.get()
        .uri(uriBuilder -> {
          var builder = uriBuilder.path("/flights")
              .queryParam("access_key", apiKey);
          if (departureIata != null && !departureIata.isBlank()) {
            builder.queryParam("dep_iata", departureIata);
          }
          if (arrivalIata != null && !arrivalIata.isBlank()) {
            builder.queryParam("arr_iata", arrivalIata);
          }
          return builder.build();
        })
        .retrieve()
        .body(AviationstackFlightsResponse.class);
  }
}
