/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class ClinicService {

	private PetRepository petRepository;

	private VetRepository vetRepository;

	private OwnerRepository ownerRepository;

	private VisitRepository visitRepository;

	private BookRepository bookRepository;

	private CauseRepository causeRepository;

	private DonationRepository donationRepository;

	@Autowired
	public ClinicService(PetRepository petRepository, VetRepository vetRepository, OwnerRepository ownerRepository,
			VisitRepository visitRepository, BookRepository bookRepository, CauseRepository causeRepository,
            DonationRepository donationRepository) {
		this.petRepository = petRepository;
		this.vetRepository = vetRepository;
		this.ownerRepository = ownerRepository;
		this.visitRepository = visitRepository;
		this.bookRepository = bookRepository;
		this.causeRepository = causeRepository;
		this.donationRepository = donationRepository;
	}

	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() throws DataAccessException {
		return petRepository.findPetTypes();
	}

	@Transactional(readOnly = true)
	public Collection<Specialty> findSpecialties() {
		return vetRepository.findSpecialties();
	}

	@Transactional(readOnly = true)
	public Owner findOwnerById(int id) throws DataAccessException {
		return ownerRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Collection<Owner> findOwnerByLastName(String lastName) throws DataAccessException {
		return ownerRepository.findByLastName(lastName);
	}

    @Transactional(readOnly = true)
    public Book findBookById(int id) throws DataAccessException {
	    return bookRepository.findById(id);
    }

	@Transactional
	public void saveOwner(Owner owner) throws DataAccessException {
		ownerRepository.save(owner);
	}

	@Transactional
	public void saveVet(Vet vet) throws DataAccessException {
		vetRepository.save(vet);
	}

	@Transactional
	public void saveVisit(Visit visit) throws DataAccessException {
		visitRepository.save(visit);
	}

	public void saveBooking(Book book) throws DataAccessException {
	    bookRepository.save(book);
    }

	@Transactional(readOnly = true)
	public Pet findPetById(int id) throws DataAccessException {
		return petRepository.findById(id);
	}

	@Transactional
	public void savePet(Pet pet) throws DataAccessException {
		petRepository.save(pet);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "vets")
	public Collection<Vet> findVets() throws DataAccessException {
		return vetRepository.findAll();
	}

	public Collection<Visit> findVisitsByPetId(int petId) {
		return visitRepository.findByPetId(petId);
	}

	public Collection<Book> findBookingsByPetOwnerId(int ownerId) {
	    return bookRepository.findByPetOwnerId(ownerId);
    }

	@Transactional
	public void deletePet(Pet pet) throws DataAccessException {

		this.petRepository.delete(pet);
	}

	@Transactional
    public void deleteBook(Book book) throws DataAccessException {
	    this.bookRepository.delete(book);
    }


	@Transactional(readOnly = true)
	public Vet findVetById(int id) throws DataAccessException {
		return vetRepository.findById(id);
	}

	@Transactional
	public void deleteVet(Vet vet) throws DataAccessException {

		this.vetRepository.delete(vet);
	}

	@Transactional(readOnly = true)
	public Visit findVisitById(int id) throws DataAccessException {
		return visitRepository.findById(id);
	}

	@Transactional
	public void deleteVisit(Visit visit) throws DataAccessException {

		this.visitRepository.delete(visit);
	}

	@Transactional
	public void deleteOwner(Owner owner) throws DataAccessException {

		this.ownerRepository.delete(owner);
	}

	@Transactional
	public Specialty findSpecialtyByName(String text) {
		return this.vetRepository.findSpecialtiesByName(text);
	}

	@Transactional(readOnly = true)
    public Cause findCauseById(int causeId) throws DataAccessException {
	    return this.causeRepository.findById(causeId);
    }

    @Transactional
    public void saveCause(Cause cause) throws DataAccessException {
	    this.causeRepository.save(cause);
    }

    @Transactional
    public void deleteCause(Cause cause) throws DataAccessException {
	    this.causeRepository.delete(cause);
    }

    @Transactional(readOnly = true)
    public Donation findDonationById(int donationId) throws DataAccessException {
	    return this.donationRepository.findById(donationId);
    }

    @Transactional(readOnly = true)
    public Collection<Donation> findDonationsByCauseId(int causeId) throws DataAccessException {
	    return this.donationRepository.findByCauseId(causeId);
    }

    @Transactional
    public void saveDonation(Donation donation) throws DataAccessException {
	    this.donationRepository.save(donation);
    }

    @Transactional
    public void deleteDonation(Donation donation) throws DataAccessException {
	    this.donationRepository.delete(donation);
    }



}
