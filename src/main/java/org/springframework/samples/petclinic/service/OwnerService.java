package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class OwnerService {

    private OwnerRepository ownerRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Transactional(readOnly = true)
    public Collection<Owner> findAllOwners() {
        return this.ownerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Owner findOwnerById(final int id) {
        return this.ownerRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Collection<Owner> findOwnerByLastName(final String lastName) {
        return this.ownerRepository.findByLastName(lastName);
    }

    @Transactional
    public void saveOwner(final Owner owner) {
        this.ownerRepository.save(owner);
    }

    @Transactional
    public void deleteOwner(final Owner owner) {

        this.ownerRepository.delete(owner);
    }
}
