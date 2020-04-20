package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.repository.DonationRepository;

import java.util.Collection;

public interface SpringDataDonationRepository extends DonationRepository, Repository<Donation, Integer> {

    @Override
    @Query("SELECT cause.donations FROM Cause cause WHERE cause.id = :id")
    Collection<Donation> findByCauseId(@Param("id") int id);
}
