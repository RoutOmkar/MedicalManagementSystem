package com.hospital.service;

import com.hospital.entity.Specialization;
import com.hospital.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecializationServiceImpl implements SpecializationService {

    @Autowired
    private SpecializationRepository specializationRepository;

    @Override
    public Specialization createSpecialization(Specialization specialization) {
        return specializationRepository.save(specialization);
    }

    @Override
    public List<Specialization> getAllSpecializations() {
        return specializationRepository.findAll();
    }

    @Override
    public Optional<Specialization> getSpecializationById(Integer id) {
        return specializationRepository.findById(id);
    }

    @Override
    public Specialization updateSpecialization(Specialization specialization) {
        return specializationRepository.save(specialization);
    }

    @Override
    public void deleteSpecialization(Integer id) {
        specializationRepository.deleteById(id);
    }

    @Override
    public Optional<Specialization> getSpecializationByName(String name) {
        return specializationRepository.findByName(name);
    }

    @Override
    public boolean existsById(Integer id) {
        return specializationRepository.existsById(id);
    }
}
