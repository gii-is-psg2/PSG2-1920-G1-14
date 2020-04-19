package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class VetService {

    private VetRepository vetRepository;

    @Autowired
    public VetService(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "vets")
    public Collection<Vet> findVets() {
        return this.vetRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Vet findVetById(final int id) {
        return this.vetRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Collection<Specialty> findSpecialties() {
        return this.vetRepository.findSpecialties();
    }

    @Transactional
    public Specialty findSpecialtyByName(final String text) {
        return this.vetRepository.findSpecialtiesByName(text);
    }

    @Transactional
    public void saveVet(final Vet vet) {
        this.vetRepository.save(vet);
    }

    @Transactional
    public void deleteVet(final Vet vet) {

        this.vetRepository.delete(vet);
    }
}
