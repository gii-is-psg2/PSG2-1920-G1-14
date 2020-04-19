package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.repository.DonationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class DonationService {

    private DonationRepository donationRepository;

    @Autowired
    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    @Transactional(readOnly = true)
    public Donation findDonationById(final int donationId) {
        return this.donationRepository.findById(donationId);
    }

    @Transactional(readOnly = true)
    public Collection<Donation> findDonationsByCauseId(final int causeId) {
        return this.donationRepository.findByCauseId(causeId);
    }

    @Transactional
    public void saveDonation(final Donation donation) {
        this.donationRepository.save(donation);
    }

    @Transactional
    public void deleteDonation(final Donation donation) {
        this.donationRepository.delete(donation);
    }
}
