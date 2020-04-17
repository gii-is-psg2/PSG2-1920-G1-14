
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.samples.petclinic.model.Cause;

public interface CauseRepository {

	void save(Cause cause);

	void delete(Cause cause);

	Cause findById(int id);

	Collection<Cause> findAll();
}
