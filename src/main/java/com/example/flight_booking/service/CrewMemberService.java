package com.example.flight_booking.service;

import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.entity.CrewMember;
import com.example.flight_booking.repository.CrewMemberRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CrewMemberService {
  private final CrewMemberRepository crewMemberRepository;

  public CrewMemberService(CrewMemberRepository crewMemberRepository) {
    this.crewMemberRepository = crewMemberRepository;
  }

  // oluşturma kaydetme

  public CrewMember createCrewMember(String firstName, String lastName, String role, Integer employeeNumber,
      String phone,
      Airline airline) {
    CrewMember crewMember = new CrewMember();
    crewMember.setFirstName(firstName);
    crewMember.setLastName(lastName);
    crewMember.setRole(role);
    crewMember.setEmployeeNumber(employeeNumber);
    crewMember.setPhone(phone);
    crewMember.setAirline(airline);
    return crewMemberRepository.save(crewMember);
  }

  // okuma listeleme

  public List<CrewMember> getAllCrewMembers() {
    return crewMemberRepository.findAll();
  }

  // id ile okuma

  public CrewMember getCrewMemberById(Long id) {
    return crewMemberRepository.findById(id).orElse(null);
  }

  // Güncelleme

  public CrewMember updateCrewMember(Long id, String firstName, String lastName, String role, Integer employeeNumber,
      String phone, Airline airline) {
    CrewMember crewMember = crewMemberRepository.findById(id).orElse(null);
    if (crewMember != null) {
      crewMember.setFirstName(firstName);
      crewMember.setLastName(lastName);
      crewMember.setRole(role);
      crewMember.setEmployeeNumber(employeeNumber);
      crewMember.setPhone(phone);
      crewMember.setAirline(airline);
      return crewMemberRepository.save(crewMember);
    }
    return null;
  }

  // Silme

  public void deleteCrewMember(Long id) {
    crewMemberRepository.deleteById(id);
  }

}
