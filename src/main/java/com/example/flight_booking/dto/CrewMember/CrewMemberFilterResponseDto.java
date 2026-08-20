package com.example.flight_booking.dto.CrewMember;

import com.example.flight_booking.entity.Airline;
import lombok.Data;

@Data
public class CrewMemberFilterResponseDto {
    private Long crewMemberId;
    private String firstName;
    private String lastName;
    private String role;
    private int employeeNumber;
    private String phone;
    private Airline airline;
}
