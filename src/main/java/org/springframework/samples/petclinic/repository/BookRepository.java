package org.springframework.samples.petclinic.repository;

import org.springframework.samples.petclinic.model.Book;

import java.util.Collection;
public interface BookRepository {

    void save(Book book) ;

    void delete(Book book) ;

    Collection<Book> findByPetOwnerId(int id);

    Book findById(int id) ;

}
