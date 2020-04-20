package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "donations")
public class Donation extends BaseEntity{

	@Column(name = "donation_date")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate date;

	@NotNull
    @Positive
	@Column(name = "amount")
	private Double amount;

	@NotNull
    @ManyToOne
	@JoinColumn
	private Owner client;

	@NotNull
	@ManyToOne
    @JoinColumn(name = "cause_id")
    private Cause cause;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Owner getClient() {
		return client;
	}

	public void setClient(Owner client) {
		this.client = client;
	}

	public Cause getCause() {
		return cause;
	}

	public void setCause(Cause cause) {
		this.cause = cause;
	}





}
