
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DonationController {

	private ClinicService clinicService;

	private static final String CREATE_OR_UPDATE_DONATION_VIEW = "donations/createDonationForm";
	private static final String VAR_AMOUNT = "amount";
    private static final String ERROR_AMOUNT = "error.amount";



	@Autowired
	public DonationController(final ClinicService clinicService) {
		this.clinicService = clinicService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/causes/{causeId}/donations/new")
	public String initCreationDonationForm(final Map<String, Object> model, @PathVariable("causeId") final int causeId) {
		Donation donation = new Donation();
		Cause cause = this.clinicService.findCauseById(causeId);
		donation.setDate(LocalDate.now());
		donation.setCause(cause);
		model.put("donation", donation);
		return CREATE_OR_UPDATE_DONATION_VIEW;
	}

	@PostMapping(value = "/causes/{causeId}/donations/new")
	public String processCreationDonationForm(@Valid final Donation donation, final BindingResult result, @PathVariable("causeId") final int causeId) {
		if (result.hasErrors()) {
			return CREATE_OR_UPDATE_DONATION_VIEW;
		} else if (this.tieneMasDeDosDecimales(donation.getAmount())) {
			result.rejectValue(VAR_AMOUNT, ERROR_AMOUNT, "Invalid format. Money can only have 2 decimal digits");
			return CREATE_OR_UPDATE_DONATION_VIEW;
		} else if (donation.getCause().getClosed()) {
			result.rejectValue(VAR_AMOUNT, ERROR_AMOUNT, "The cause has been already completed");
			return CREATE_OR_UPDATE_DONATION_VIEW;
		} else if (donation.getAmount() <= 0) {
			result.rejectValue(VAR_AMOUNT, ERROR_AMOUNT, "The donation can not be 0");
			return CREATE_OR_UPDATE_DONATION_VIEW;
		} else if (donation.getCause().getAmount() + donation.getAmount() > donation.getCause().getBudgetTarget()) {
			result.rejectValue(VAR_AMOUNT, ERROR_AMOUNT, "The donation cant be higher than the remaining amount");
			return CREATE_OR_UPDATE_DONATION_VIEW;
		} else if (donation.getClient() == null || donation.getClient().equals("")) {
			result.rejectValue(VAR_AMOUNT, ERROR_AMOUNT, "You must introduce a name");
			return CREATE_OR_UPDATE_DONATION_VIEW;
		} else {
			donation.setDate(LocalDate.now());
			this.clinicService.saveDonation(donation);
			this.clinicService.saveCause(donation.getCause());
			return "redirect:/causes";
		}
	}

	private boolean tieneMasDeDosDecimales(final Double amount) {
		boolean res = true;
		Double n = Math.floor(amount * 100) / 100;
		if (amount - n == 0.) {
			res = false;
		}
		return res;
	}

}
