package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Book;
import org.springframework.samples.petclinic.model.Cause;

import java.util.Collection;
public interface CauseRepository {

    void save(Cause cause) throws DataAccessException;
    
    void delete(Cause cause) throws DataAccessException;

    Cause findById(int id) throws DataAccessException;

}
