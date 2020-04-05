package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DonationController {

	private ClinicService clinicService;


	@Autowired
	public DonationController(final ClinicService clinicService) {
		this.clinicService = clinicService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/causes/{causeId}/donations/new")
	public String initCreationDonationForm(final Map<String, Object> model , @PathVariable("causeId") int causeId) {
		Donation donation = new Donation();
		Cause cause = this.clinicService.findCauseById(causeId);
		donation.setDate(LocalDate.now());
		donation.setCause(cause);
		model.put("donation", donation);
		return "donations/createDonationForm";
	}
	
	@PostMapping(value = "/causes/{causeId}/donations/new")
	public String processCreationDonationForm(@Valid final Donation donation, @PathVariable("causeId") final int causeId, final BindingResult result) {
		Cause cause = this.clinicService.findCauseById(causeId);
		donation.setDate(LocalDate.now());
		donation.setCause(cause);
		this.clinicService.saveDonation(donation);
		return "redirect:/causes";
	}
	
}
