/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.repository.*;
import org.springframework.samples.petclinic.service.exceptions.PartialOverlapDateException;
import org.springframework.samples.petclinic.service.exceptions.TotalOverlapDateException;
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
	public ClinicService(final PetRepository petRepository, final VetRepository vetRepository,
			final OwnerRepository ownerRepository, final VisitRepository visitRepository,
			final BookRepository bookRepository, final CauseRepository causeRepository,
			final DonationRepository donationRepository) {
		this.petRepository = petRepository;
		this.vetRepository = vetRepository;
		this.ownerRepository = ownerRepository;
		this.visitRepository = visitRepository;
		this.bookRepository = bookRepository;
		this.causeRepository = causeRepository;
		this.donationRepository = donationRepository;
	}

	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() {
		return this.petRepository.findPetTypes();
	}

	@Transactional(readOnly = true)
	public Collection<Specialty> findSpecialties() {
		return this.vetRepository.findSpecialties();
	}

	@Transactional(readOnly = true)
	public Owner findOwnerById(final int id) {
		return this.ownerRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Collection<Owner> findOwnerByLastName(final String lastName) {
		return this.ownerRepository.findByLastName(lastName);
	}

	@Transactional(readOnly = true)
	public Book findBookById(final int id) {
		return this.bookRepository.findById(id);
	}

	@Transactional
	public void saveOwner(final Owner owner) {
		this.ownerRepository.save(owner);
	}

	@Transactional
	public void saveVet(final Vet vet) {
		this.vetRepository.save(vet);
	}

	@Transactional
	public void saveVisit(final Visit visit) {
		this.visitRepository.save(visit);
	}

	public void saveBooking(Book book) throws TotalOverlapDateException, PartialOverlapDateException {
		Collection<Book> books = bookRepository.findByPetOwnerId(book.getPet().getId());
		Collection<Book> bookStart = books;
		Collection<Book> bookFinish = books;

		// CAMBIAR LAMBDA EXPRESION
		List<LocalDate> startDates = bookStart.stream().map(Book::getStart).collect(Collectors.toList());
		List<LocalDate> finishDates = bookFinish.stream().map(Book::getFinish).collect(Collectors.toList());

		String overlapType = overlapType(startDates, finishDates, book.getStart(), book.getFinish());
		if (!overlapType.equals("")) {
			if (overlapType.equals("complete")) {
				throw new TotalOverlapDateException();
			} else {
				throw new PartialOverlapDateException();
			}
		} else {
			bookRepository.save(book);
		}
	}

	private String overlapType(List<LocalDate> startDates, List<LocalDate> finishDates, LocalDate newBookStart,
			LocalDate newBookFinish) {
		int i = 0;
		String res = "";
		String partial = "partial";
		String complete = "complete";
		while (i < startDates.size()) {
			LocalDate startBookI = startDates.get(i);
			LocalDate finishBookI = finishDates.get(i);
			if (((newBookStart.equals(startBookI) || newBookStart.isAfter(startBookI))
					&& ((newBookFinish.equals(finishBookI)) || newBookFinish.isBefore(finishBookI)))
					|| (newBookStart.equals(startBookI) || newBookStart.isBefore(startBookI))
							&& ((newBookFinish.equals(finishBookI)) || newBookFinish.isAfter(finishBookI))) {
				res = complete;
			} else if (startBookI.isBefore(newBookFinish)
					&& (finishBookI.isAfter(newBookFinish) || finishBookI.equals(newBookFinish))) {
				res = partial;
			} else if (startBookI.isBefore(newBookStart) && finishBookI.isAfter(newBookStart)) {
				res = partial;
			} else if (startBookI.equals(newBookFinish) || finishBookI.equals(newBookStart)) {
				res = partial;
			}
			i++;
		}
		return res;
	}

	@Transactional(readOnly = true)
	public Pet findPetById(final int id) {
		return this.petRepository.findById(id);
	}

	@Transactional
	public void savePet(final Pet pet) {
		this.petRepository.save(pet);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "vets")
	public Collection<Vet> findVets() {
		return this.vetRepository.findAll();
	}

	public Collection<Visit> findVisitsByPetId(final int petId) {
		return this.visitRepository.findByPetId(petId);
	}

	public Collection<Book> findBookingsByPetOwnerId(final int ownerId) {
		return this.bookRepository.findByPetOwnerId(ownerId);
	}

	public void deletePet(Pet pet) {
		this.petRepository.delete(pet);
	}

	@Transactional
	public void deleteBook(final Book book) {
		this.bookRepository.delete(book);
	}

	@Transactional(readOnly = true)
	public Vet findVetById(final int id) {
		return this.vetRepository.findById(id);
	}

	@Transactional
	public void deleteVet(final Vet vet) {

		this.vetRepository.delete(vet);
	}

	@Transactional(readOnly = true)
	public Visit findVisitById(final int id) {
		return this.visitRepository.findById(id);
	}

	@Transactional
	public void deleteVisit(final Visit visit) {

		this.visitRepository.delete(visit);
	}

	@Transactional
	public void deleteOwner(final Owner owner) {

		this.ownerRepository.delete(owner);
	}

	@Transactional
	public Specialty findSpecialtyByName(final String text) {
		return this.vetRepository.findSpecialtiesByName(text);
	}

	@Transactional(readOnly = true)
	public Cause findCauseById(final int causeId) {
		return this.causeRepository.findById(causeId);
	}

	@Transactional(readOnly = true)
	public Collection<Cause> findCauses() {
		return this.causeRepository.findAll();
	}

	@Transactional
	public void saveCause(final Cause cause) {
		this.causeRepository.save(cause);
	}

	@Transactional
	public void deleteCause(final Cause cause) {
		this.causeRepository.delete(cause);
	}

	@Transactional(readOnly = true)
	public Donation findDonationById(final int donationId) {
		return this.donationRepository.findById(donationId);
	}

	@Transactional(readOnly = true)
	public Collection<Donation> findDonationsByCauseId(final int causeId) {
		return this.donationRepository.findByCauseId(causeId);
	}

	@Transactional
	public void saveDonation(final Donation donation) {
		this.donationRepository.save(donation);
	}

	@Transactional
	public void deleteDonation(final Donation donation) {
		this.donationRepository.delete(donation);
	}

}
