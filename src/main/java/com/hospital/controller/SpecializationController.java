package com.hospital.controller;

import com.hospital.entity.Specialization;
import com.hospital.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specializations")
@CrossOrigin(origins = "*")
public class SpecializationController {

    @Autowired
    private SpecializationService specializationService;

    @GetMapping
    public List<Specialization> getAllSpecializations() {
        return specializationService.getAllSpecializations();
    }

    @GetMapping("/{id}")
    public Specialization getSpecializationById(@PathVariable Integer id) {
        return specializationService.getSpecializationById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialization not found with id: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Specialization createSpecialization(@RequestBody Specialization specialization) {
        return specializationService.createSpecialization(specialization);
    }

    @PutMapping("/{id}")
    public Specialization updateSpecialization(@PathVariable Integer id, @RequestBody Specialization specialization) {
        if (!specializationService.existsById(id)) {
            throw new ResourceNotFoundException("Specialization not found with id: " + id);
        }
        return specializationService.updateSpecialization(specialization);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSpecialization(@PathVariable Integer id) {
        if (!specializationService.existsById(id)) {
            throw new ResourceNotFoundException("Specialization not found with id: " + id);
        }
        specializationService.deleteSpecialization(id);
    }

    @GetMapping("/name/{name}")
    public Specialization getSpecializationByName(@PathVariable String name) {
        return specializationService.getSpecializationByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Specialization not found with name: " + name));
    }

    // Custom Exception for 404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) { super(message); }
    }
}
