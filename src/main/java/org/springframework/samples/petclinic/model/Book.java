
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "bookings")
public class Book extends BaseEntity {

	@Column(name = "start")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	start;

	@Column(name = "finish")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	finish;

	@Column(name = "details")
	private String		details;

	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet			pet;


	public LocalDate getStart() {
		return this.start;
	}

	public void setStart(final LocalDate start) {
		this.start = start;
	}

	public LocalDate getFinish() {
		return this.finish;
	}

	public void setFinish(final LocalDate finish) {
		this.finish = finish;
	}

	public Pet getPet() {
		return this.pet;
	}

	public void setPet(final Pet pet) {
		this.pet = pet;
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(final String details) {
		this.details = details;
	}
}
