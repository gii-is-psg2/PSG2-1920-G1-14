package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class VisitService {

    private VisitRepository visitRepository;

    @Autowired
    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public Collection<Visit> findVisitsByPetId(final int petId) {
        return this.visitRepository.findByPetId(petId);
    }

    @Transactional(readOnly = true)
    public Visit findVisitById(final int id) {
        return this.visitRepository.findById(id);
    }

    @Transactional
    public void saveVisit(final Visit visit) {
        this.visitRepository.save(visit);
    }

    @Transactional
    public void deleteVisit(final Visit visit) {

        this.visitRepository.delete(visit);
    }
}
