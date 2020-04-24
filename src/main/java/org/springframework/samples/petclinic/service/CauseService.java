package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class CauseService {

    private CauseRepository causeRepository;

    @Autowired
    public CauseService(CauseRepository causeRepository) {
        this.causeRepository = causeRepository;
    }

    @Transactional(readOnly = true)
    public Cause findCauseById(final int causeId) {
        return this.causeRepository.findById(causeId);
    }

    @Transactional(readOnly = true)
    public Collection<Cause> findCauses() {
        return this.causeRepository.findAll();
    }

    @Transactional
    public void saveCause(final Cause cause) {
        this.causeRepository.save(cause);
    }

    @Transactional
    public void deleteCause(final Cause cause) {
        this.causeRepository.delete(cause);
    }
}
