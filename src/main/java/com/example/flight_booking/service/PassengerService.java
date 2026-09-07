package com.example.flight_booking.service;

import com.example.flight_booking.dto.Passenger.PassengerCreateRequestDto;
import com.example.flight_booking.dto.Passenger.PassengerFilterResponseDto;
import com.example.flight_booking.dto.Passenger.PassengerUpdateRequestDto;
import com.example.flight_booking.entity.Passenger;
import com.example.flight_booking.mapper.PassengerMapper;
import com.example.flight_booking.repository.PassengerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PassengerService {

  private static final Logger logger = LoggerFactory.getLogger(PassengerService.class);
  private final PassengerRepository passengerRepository;
  private final PassengerMapper passengerMapper;

  public PassengerService(PassengerRepository passengerRepository, PassengerMapper passengerMapper) {
    this.passengerRepository = passengerRepository;
    this.passengerMapper = passengerMapper;
  }

  public Passenger getPassengerEntityById(Long id) {
    logger.debug("Yolcu aranıyor. passengerId={}", id);
    return passengerRepository.findById(id)
        .orElseThrow(() -> {
          logger.warn("Yolcu bulunamadı. passengerId={}", id);
          return new ResponseStatusException(HttpStatus.NOT_FOUND,
              "Passenger not defined. Id is: " + id);
        });
  }

  public PassengerFilterResponseDto getPassengerById(Long id) {
    Passenger passenger = getPassengerEntityById(id);
    return passengerMapper.toFilterResponseDto(passenger);
  }

  public Passenger createPassenger(PassengerCreateRequestDto create) {
    logger.info("Yolcu oluşturuluyor.");

    Passenger passenger = passengerMapper.toEntity(create);
    Passenger savedPassenger = passengerRepository.save(passenger);

    logger.info("Yolcu oluşturuldu. passengerId={}", savedPassenger.getPassengerId());
    return savedPassenger;
  }

  public Passenger updatePassenger(Long id, PassengerUpdateRequestDto update) {
    logger.info("Yolcu güncelleniyor. passengerId={}", id);

    Passenger passenger = getPassengerEntityById(id);
    passengerMapper.updateEntity(passenger, update);
    Passenger savedPassenger = passengerRepository.save(passenger);

    logger.info("Yolcu güncellendi. passengerId={}", savedPassenger.getPassengerId());
    return savedPassenger;
  }

  public void deletePassenger(Long id) {
    logger.info("Yolcu siliniyor. passengerId={}", id);

    passengerRepository.delete(getPassengerEntityById(id));

    logger.info("Yolcu silindi. passengerId={}", id);
  }
}
