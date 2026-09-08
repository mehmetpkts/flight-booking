package com.example.flight_booking.service;

import com.example.flight_booking.dto.Airline.AirlineCreateRequestDto;
import com.example.flight_booking.dto.Airline.AirlineFilterResponseDto;
import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.exception.ResourceNotFoundException;
import com.example.flight_booking.mapper.AirlineMapper;
import com.example.flight_booking.repository.AirlineRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AirlineService {

  private final AirlineRepository airlineRepository;
  private final AirlineMapper airlineMapper;
  private static final Logger logger = LoggerFactory.getLogger(AirlineService.class);

  public AirlineService(AirlineRepository airlineRepository, AirlineMapper airlineMapper) {
    this.airlineRepository = airlineRepository;
    this.airlineMapper = airlineMapper;
  }


  public Airline getAirlineEntityById(Long id){
    logger.debug("Havayolu aranıyor. AirlineId: {}", id);
    return airlineRepository.findById(id).orElseThrow(() -> {
      logger.warn("Havayolu Bulunamadı. AirlineId: {}", id);
      return new ResourceNotFoundException("Airline", id);
    });
  }

  public List<AirlineFilterResponseDto> getAllAirlines() {
    List<Airline> airlines = airlineRepository.findAll();
    logger.debug("Havayolları listeleniyor. Toplam kayıt sayısı: {}", airlines.size());

    return airlines.stream()
        .map(airlineMapper::toFilterResponseDto)
        .toList();
  }

  public AirlineFilterResponseDto getAirlineById(Long id) {
    Airline airline = getAirlineEntityById(id);
    return airlineMapper.toFilterResponseDto(airline);
  }

  public Airline createAirline(AirlineCreateRequestDto create){
    logger.info("Airline oluşturuluyor. name={}, iataCode={}",
        create.getName(), create.getIataCode());

    Airline airline = airlineMapper.toEntity(create);
    Airline savedAirline = airlineRepository.save(airline);

    logger.info("Airline oluşturuldu. airlineId={}, iataCode={}",
        savedAirline.getAirlineId(), savedAirline.getIataCode());
    return savedAirline;
  }

  public void deleteAirline(Long id){
    logger.info("Airline siliniyor. airlineId={}", id);

    airlineRepository.delete(getAirlineEntityById(id));

    logger.info("Airline silindi. airlineId={}", id);
  }


}
