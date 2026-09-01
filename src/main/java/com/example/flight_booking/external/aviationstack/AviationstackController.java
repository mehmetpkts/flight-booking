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

  @GetMapping("/flights/by-number")
  public ResponseEntity<AviationstackFlightsResponse> getByFlightIata(
          @RequestParam String flightIata) {
    return ResponseEntity.ok(aviationstackService.getFlights(flightIata));
  }

  @GetMapping("/flights/search")
  public ResponseEntity<AviationstackFlightsResponse> search(
          @RequestParam(required = false) String depIata,
          @RequestParam(required = false) String arrIata) {
    return ResponseEntity.ok(aviationstackService.searchFlights(depIata, arrIata));
  }
}
