package com.example.flight_booking.service;

import com.example.flight_booking.dto.Airport.AirportCreateRequestDto;
import com.example.flight_booking.dto.Airport.AirportFilterResponseDto;
import com.example.flight_booking.entity.Airport;
import com.example.flight_booking.mapper.AirportMapper;
import com.example.flight_booking.repository.AirportRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AirportService {

  private final AirportRepository airportRepository;
  private final AirportMapper airportMapper;
  private static final Logger logger = LoggerFactory.getLogger(AirportService.class);

  public AirportService(AirportRepository airportRepository, AirportMapper airportMapper) {
    this.airportRepository = airportRepository;
    this.airportMapper = airportMapper;
  }

  public Airport getAirportEntityById(Long id) {
    logger.debug("Havalimanı aranıyor! AirportId: {}", id);
    return airportRepository.findById(id).orElseThrow(() -> {logger.warn("Havalimanı bulunamadı, AirportId: {}",id);
      return new ResponseStatusException(
              HttpStatus.NOT_FOUND, "Airline id is not: "+ id
      );
    });
  }

  public AirportFilterResponseDto getAirportById(Long id){
    Airport airport = getAirportEntityById(id);
    return airportMapper.toFilterResponseDto(airport);
  }

  public Airport createAirport(AirportCreateRequestDto create) {
    logger.info("Airport oluşturuluyor. name={}, iataCode={}",
        create.getName(), create.getIataCode());

    Airport airport = airportMapper.toEntity(create);
    Airport savedAirport = airportRepository.save(airport);

    logger.info("Airport oluşturuldu. airportId={}, iataCode={}",
        savedAirport.getAirportId(), savedAirport.getIataCode());
    return savedAirport;
  }

  public void deleteAirport(Long id) {
    logger.info("Airport siliniyor. airportId={}", id);

    Airport airport = getAirportEntityById(id);
    airportRepository.delete(airport);

    logger.info("Airport silindi. airportId={}", id);
  }

}
