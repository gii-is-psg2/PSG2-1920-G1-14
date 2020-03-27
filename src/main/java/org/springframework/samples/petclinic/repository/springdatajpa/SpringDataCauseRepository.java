package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Book;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.BookRepository;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.samples.petclinic.repository.DonationRepository;

import java.util.Collection;

public interface SpringDataCauseRepository extends CauseRepository, Repository<Cause, Integer> {

	@Override
	@Query("SELECT cause FROM Cause cause left join fetch cause.donations WHERE cause.id =:id")
	public Cause findById(@Param("id") int id);
  
}
