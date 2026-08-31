package com.example.flight_booking.external.aviationstack;

import com.example.flight_booking.external.aviationstack.dto.AviationstackFlightsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aviationstack")
public class AviationstackController {

  private final AviationstackService aviationstackService;

  public AviationstackController(AviationstackService aviationstackService) {
    this.aviationstackService = aviationstackService;
  }

  @GetMapping("/flights")
  public ResponseEntity<AviationstackFlightsResponse> getFlights(
      @RequestParam(required = false) String flightIata,
      @RequestParam(required = false) String depIata,
      @RequestParam(required = false) String arrIata) {
    if (flightIata != null && !flightIata.isBlank()) {
      return ResponseEntity.ok(aviationstackService.getFlights(flightIata));
    }
    return ResponseEntity.ok(aviationstackService.searchFlights(depIata, arrIata));
  }
}
