package org.springframework.samples.petclinic.repository;

import org.springframework.samples.petclinic.model.Donation;

import java.util.Collection;
public interface DonationRepository {

    void save(Donation donation);

    void delete(Donation donation);

    Collection<Donation> findByCauseId(int id);

    Donation findById(int id);

}
