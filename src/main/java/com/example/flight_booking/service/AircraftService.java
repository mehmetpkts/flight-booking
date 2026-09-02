package com.example.flight_booking.service;

import com.example.flight_booking.dto.Aircraft.AircraftCreateRequestDto;
import com.example.flight_booking.dto.Aircraft.AircraftFilterResponseDto;
import com.example.flight_booking.dto.Aircraft.AircraftUpdateRequestDto;
import com.example.flight_booking.entity.Aircraft;
import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.mapper.AircraftMapper;
import com.example.flight_booking.repository.AircraftRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AircraftService {

  private final AircraftRepository aircraftRepository;
  private final AirlineService airlineService;
  private final AircraftMapper aircraftMapper;
  private static final Logger logger = LoggerFactory.getLogger(AircraftService.class);

  public AircraftService(
      AircraftRepository aircraftRepository,
      AirlineService airlineService,
      AircraftMapper aircraftMapper) {
    this.aircraftRepository = aircraftRepository;
    this.airlineService = airlineService;
    this.aircraftMapper = aircraftMapper;
  }

  // bu kod yapısı exception dosyası oluşturulduktan sonra daha sade bir biçimde yazılacak.
  public Aircraft getAircraftEntityById(Long id){
    logger.debug("Uçak aranıyor! aircraftId = {}", id);
    return aircraftRepository.findById(id).orElseThrow(() ->{
      logger.warn("Uçak bulunamadı aircraftId: {}", id);
      return new ResponseStatusException(
              HttpStatus.NOT_FOUND, "aircraf id is not: " + id
      );
    });
  }

  private Airline getAirlineEntityById(Long id) {
    return airlineService.getAirlineEntityById(id);
  }

  public AircraftFilterResponseDto getAircraftById(Long id){
    Aircraft aircraft = getAircraftEntityById(id);
    return aircraftMapper.toFilterResponseDto(aircraft);
  }

  public Aircraft createAircraft(AircraftCreateRequestDto create) {
    logger.info("Aircraft oluşturuluyor. airlineId={}", create.getAirlineId());

    Airline airline = getAirlineEntityById(create.getAirlineId());
    Aircraft aircraft = aircraftMapper.toEntity(create, airline);
    Aircraft savedAircraft = aircraftRepository.save(aircraft);

    logger.info("Aircraft oluşturuldu. aircraftId={}, airlineId={}",
        savedAircraft.getAircraftId(), airline.getAirlineId());
    return savedAircraft;
  }


  public Aircraft updateAircraft(Long id, AircraftUpdateRequestDto update) {
    logger.info("Aircraft güncelleniyor. aircraftId={}, airlineId={}",
        id, update.getAirlineId());

    Airline airline = getAirlineEntityById(update.getAirlineId());
    Aircraft aircraft = getAircraftEntityById(id);
    aircraftMapper.updateEntity(aircraft, update, airline);
    Aircraft savedAircraft = aircraftRepository.save(aircraft);

    logger.info("Aircraft güncellendi. aircraftId={}", savedAircraft.getAircraftId());
    return savedAircraft;
  }

  public void deleteAircraft(Long id) {
    logger.info("Aircraft siliniyor. aircraftId={}", id);

    aircraftRepository.delete(getAircraftEntityById(id));

    logger.info("Aircraft silindi. aircraftId={}", id);
  }

}
