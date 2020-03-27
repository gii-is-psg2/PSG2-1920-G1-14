package org.springframework.samples.petclinic.model;

import java.beans.Transient;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "causes")
public class Cause extends BaseEntity{

	@Column(name = "name")
	@NotBlank
	private String name;
	
	@Column(name = "description")
	@NotBlank
	private String description;
	
	@Column(name = "budget_target")
	@NotNull
	private Double budgetTarget;
	
	@Column(name = "organization")
	@NotBlank
	private String organization;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cause", fetch = FetchType.EAGER)
    private Set<Donation> donations;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getBudgetTarget() {
		return budgetTarget;
	}

	public void setBudgetTarget(Double budgetTarget) {
		this.budgetTarget = budgetTarget;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public Set<Donation> getDonations() {
		return donations;
	}

	public void setDonations(Set<Donation> donations) {
		this.donations = donations;
	}
	
	@Transient
	public Double getAmount() {
		Double res = 0.0;
		for(Donation d : getDonations()) {
			res = res+d.getAmount();
		}
			return res;
	}
	
	@Transient
	public Boolean getClosed() {
		Boolean res = false;		
		if(getAmount()>= getBudgetTarget()) {
			res = true;
		}
        	return res;
	}
	
	
	
}
