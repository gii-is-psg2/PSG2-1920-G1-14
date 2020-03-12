package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Book;

import java.util.Collection;
public interface BookRepository {

    void save(Book book) throws DataAccessException;

    void delete(Book book) throws DataAccessException;

    Collection<Book> findByPetOwnerId(int id) throws DataAccessException;

    Book findById(int id) throws DataAccessException;

}
