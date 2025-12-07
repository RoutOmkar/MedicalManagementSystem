package com.hospital.repository;

import com.hospital.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

    // Find specialization by exact name
    Optional<Specialization> findByName(String name);
}
