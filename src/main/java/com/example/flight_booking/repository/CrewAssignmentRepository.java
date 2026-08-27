package com.example.flight_booking.repository;

import com.example.flight_booking.entity.CrewAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewAssignmentRepository extends JpaRepository<CrewAssignment, Long> {

  boolean existsByFlight_FlightIdAndCrewMember_CrewMemberId(Long flightId, Long crewMemberId);

  boolean existsByFlight_FlightIdAndCrewMember_CrewMemberIdAndAssignmentIdNot(
      Long flightId, Long crewMemberId, Long assignmentId);

}
