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
	public String initCreationDonationForm(final Map<String, Object> model, @PathVariable("causeId") int causeId) {
		Donation donation = new Donation();
		Cause cause = this.clinicService.findCauseById(causeId);
		donation.setDate(LocalDate.now());
		donation.setCause(cause);
		model.put("donation", donation);
		return "donations/createDonationForm";
	}

	@PostMapping(value = "/causes/{causeId}/donations/new")
	public String processCreationDonationForm(@Valid final Donation donation, final BindingResult result,
			@PathVariable("causeId") final int causeId) {
        if (result.hasErrors()) {
            return "donations/createDonationForm";
        } else if (TieneMasDeDosDecimales(donation.getAmount())) {
			result.rejectValue("amount", "error.amount",
					"Invalid format. Money can only have 2 decimal digits");
			return "donations/createDonationForm";
		} else if (donation.getCause().getClosed()) {
            result.rejectValue("amount", "error.amount", "The cause has been already completed");
            return "donations/createDonationForm";
        } else if ( donation.getAmount() <= 0) {
            result.rejectValue("amount", "error.amount", "The donation can not be 0");
            return "donations/createDonationForm";
        } else if (donation.getCause().getAmount() + donation.getAmount() > donation.getCause().getBudgetTarget()) {
            result.rejectValue("amount", "error.amount", "The donation cant be higher than the remaining amount");
            return "donations/createDonationForm";
        }else if(donation.getClient()==null || donation.getClient()=="") {
            result.rejectValue("client", "error.client", "You must introduce a name");
            return "donations/createDonationForm";
        } else {
            donation.setDate(LocalDate.now());
            this.clinicService.saveDonation(donation);
            this.clinicService.saveCause(donation.getCause());
            return "redirect:/causes";
        }
	}

	private boolean TieneMasDeDosDecimales(Double amount) {
		 Boolean res = true;
         Double n =  Math.floor(amount * 100) / 100;
         if (amount - n == 0.) {
             res = false;
         } 
         return res;
	}

}
