
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CauseController {

	private ClinicService clinicService;


	@Autowired
	public CauseController(final ClinicService clinicService) {
		this.clinicService = clinicService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/causes")
	public String showCauses(final ModelMap modelMap) {
		String vista = "causes/causesList";
		Collection<Cause> results = this.clinicService.findCauses();
		modelMap.addAttribute("causes", results);
		return vista;
	}

	@GetMapping(value = "/causes/{causeId}")
	public ModelAndView showCause(@PathVariable("causeId") final int causeId, final Map<String, Object> model) {
		Collection<Donation> donations;
		donations = this.clinicService.findDonationsByCauseId(causeId);
		model.put("donations", donations);
		ModelAndView mav = new ModelAndView("causes/causeDetails");
		mav.addObject("cause", this.clinicService.findCauseById(causeId));
		return mav;
	}

	@GetMapping(value = "/causes/new")
	public String initCreationForm(final Map<String, Object> model) {
		Cause cause = new Cause();
		model.put("cause", cause);
		return "causes/createCauseForm";
	}

	@PostMapping(value = "/causes/new")
	public String processCreationForm(@Valid final Cause cause, final BindingResult result) {
		if (result.hasErrors()) {
			return "/causes/createCauseForm";
		} else {
			this.clinicService.saveCause(cause);
			return "redirect:/causes";
		}
	}
}
