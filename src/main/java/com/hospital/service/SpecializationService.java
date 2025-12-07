package com.hospital.service;

import com.hospital.entity.Specialization;
import java.util.List;
import java.util.Optional;

public interface SpecializationService {

    Specialization createSpecialization(Specialization specialization);

    List<Specialization> getAllSpecializations();

    Optional<Specialization> getSpecializationById(Integer id);

    Specialization updateSpecialization(Specialization specialization); // ID included in object

    void deleteSpecialization(Integer id);

    Optional<Specialization> getSpecializationByName(String name);

    boolean existsById(Integer id);
}
