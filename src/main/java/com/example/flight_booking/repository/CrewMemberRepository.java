package com.example.flight_booking.repository;

import com.example.flight_booking.entity.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CrewMember entity'si için repository interface'i.
 * Uçak görevlisinin bilgilerini veritabanında yönetir.
 */
@Repository
public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {

}
