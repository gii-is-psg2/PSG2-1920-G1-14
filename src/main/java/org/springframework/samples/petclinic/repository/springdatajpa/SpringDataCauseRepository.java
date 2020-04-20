package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.repository.CauseRepository;

public interface SpringDataCauseRepository extends CauseRepository, Repository<Cause, Integer> {

	@Override
	@Query("SELECT cause FROM Cause cause left join fetch cause.donations WHERE cause.id =:id")
	public Cause findById(@Param("id") int id);

}
