package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Book;
import org.springframework.samples.petclinic.repository.BookRepository;

import java.util.Collection;

public interface SpringDataBookRepository extends BookRepository, Repository<Book, Integer> {

    @Override
    @Query("SELECT pet.bookings FROM Owner owner JOIN owner.pets pet WHERE owner.id = :id")
    Collection<Book> findByPetOwnerId(@Param("id") int id);
}
