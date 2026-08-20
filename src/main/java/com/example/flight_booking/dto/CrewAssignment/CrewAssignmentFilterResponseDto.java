package com.example.flight_booking.dto.CrewAssignment;

import com.example.flight_booking.entity.CrewMember;
import com.example.flight_booking.entity.Flight;
import lombok.Data;

@Data
public class CrewAssignmentFilterResponseDto {
    private Long assignmentId;
    private Flight flight;
    private CrewMember crewMember;
    private String duty;
}
