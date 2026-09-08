package com.example.flight_booking.service;

import com.example.flight_booking.dto.CrewMember.CrewMemberCreateRequestDto;
import com.example.flight_booking.dto.CrewMember.CrewMemberFilterResponseDto;
import com.example.flight_booking.dto.CrewMember.CrewMemberUpdateRequestDto;
import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.entity.CrewMember;
import com.example.flight_booking.exception.ResourceNotFoundException;
import com.example.flight_booking.mapper.CrewMemberMapper;
import com.example.flight_booking.repository.CrewMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CrewMemberService {
  private static final Logger logger = LoggerFactory.getLogger(CrewMemberService.class);
  private final CrewMemberRepository crewMemberRepository;
  private final AirlineService airlineService;
  private final CrewMemberMapper crewMemberMapper;

  public CrewMemberService(
      CrewMemberRepository crewMemberRepository,
      AirlineService airlineService,
      CrewMemberMapper crewMemberMapper) {
    this.crewMemberRepository = crewMemberRepository;
    this.airlineService = airlineService;
    this.crewMemberMapper = crewMemberMapper;
  }

  public CrewMember getCrewMemberEntityById(Long id) {
    logger.debug("Ekip üyesi aranıyor. crewMemberId={}", id);
    return crewMemberRepository.findById(id)
        .orElseThrow(() -> {
          logger.warn("Ekip üyesi bulunamadı. crewMemberId={}", id);
          return new ResourceNotFoundException("Crew member", id);
        });
  }

  private Airline getAirlineEntityById(Long id) {
    return airlineService.getAirlineEntityById(id);
  }

  public CrewMemberFilterResponseDto getCrewMemberById(Long id){
    CrewMember crewMember = getCrewMemberEntityById(id);
    return crewMemberMapper.toFilterResponseDto(crewMember);
  }

  public CrewMember createCrewMember(CrewMemberCreateRequestDto create) {
    logger.info("Ekip üyesi oluşturuluyor. airlineId={}, role={}",
        create.getAirlineId(), create.getRole());

    Airline airline = getAirlineEntityById(create.getAirlineId());
    CrewMember crewMember = crewMemberMapper.toEntity(create, airline);
    CrewMember savedCrewMember = crewMemberRepository.save(crewMember);

    logger.info("Ekip üyesi oluşturuldu. crewMemberId={}, airlineId={}",
        savedCrewMember.getCrewMemberId(), airline.getAirlineId());
    return savedCrewMember;
  }


  public CrewMember updateCrewMember(Long id, CrewMemberUpdateRequestDto update) {
    logger.info("Ekip üyesi güncelleniyor. crewMemberId={}, airlineId={}, role={}",
        id, update.getAirlineId(), update.getRole());

    Airline airline = getAirlineEntityById(update.getAirlineId());
    CrewMember crewMember = getCrewMemberEntityById(id);
    crewMemberMapper.updateEntity(crewMember, update, airline);
    CrewMember savedCrewMember = crewMemberRepository.save(crewMember);

    logger.info("Ekip üyesi güncellendi. crewMemberId={}", savedCrewMember.getCrewMemberId());
    return savedCrewMember;
  }

  public void deleteCrewMember(Long id) {
    logger.info("Ekip üyesi siliniyor. crewMemberId={}", id);

    CrewMember crewMember = getCrewMemberEntityById(id);
    crewMemberRepository.delete(crewMember);

    logger.info("Ekip üyesi silindi. crewMemberId={}", id);
  }

}
