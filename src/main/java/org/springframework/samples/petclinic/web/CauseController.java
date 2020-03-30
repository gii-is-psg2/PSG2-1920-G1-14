
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String ShowCauses(final Cause cause, final BindingResult result, final Map<String, Object> model) {
		Collection<Cause> results = this.clinicService.findCauses();
		model.put("causes", results);
		return "causes/causesList";
	}

	@GetMapping(value = "/causes/{causeId}")
	public ModelAndView showCause(@PathVariable("causeId") final int causeId) {
		ModelAndView mav = new ModelAndView("causes/causeDetails");
		mav.addObject(this.clinicService.findCauseById(causeId));
		return mav;
	}

}
