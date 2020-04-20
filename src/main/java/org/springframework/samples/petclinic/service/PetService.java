package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class PetService {

    private PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Transactional(readOnly = true)
    public Collection<PetType> findPetTypes() {
        return this.petRepository.findPetTypes();
    }

    @Transactional(readOnly = true)
    public Pet findPetById(final int id) {
        return this.petRepository.findById(id);
    }

    @Transactional
    public void savePet(final Pet pet) {
        this.petRepository.save(pet);
    }

    public void deletePet(Pet pet) {
        this.petRepository.delete(pet);
    }
}
