package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Book;
import org.springframework.samples.petclinic.model.Donation;

import java.util.Collection;
public interface DonationRepository {

    void save(Donation donation) throws DataAccessException;

    void delete(Donation donation) throws DataAccessException;

    Collection<Donation> findByCauseId(int id) throws DataAccessException;

    Donation findById(int id) throws DataAccessException;

}
