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
package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
public class VetController {

	private static final String VIEWS_VET_CREATE_OR_UPDATE_FORM = "vets/createOrUpdateVetForm";
	private static final String SUCCESSFUL_REDIRECT_VIEW = "redirect:/vets";

	private VetService vetService;

	@Autowired
	public VetController(VetService vetService) {
		this.vetService = vetService;
	}

	@GetMapping(value = { "/vets"})
	public String showVetList(Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for Object-Xml mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		model.put("vets", vets);
		return "vets/vetList";
	}

	@GetMapping(value = {"/vets/new"})
	public String initCreationForm(ModelMap model) {
		Vet vet = new Vet();
		model.put("vet", vet);
		return VIEWS_VET_CREATE_OR_UPDATE_FORM;
	}


	@PostMapping(value = "/vets/new")
	public String processCreatingForm(@Valid Vet vet, BindingResult result) {
		if(result.hasErrors()) {
			return VIEWS_VET_CREATE_OR_UPDATE_FORM;
		} else {
			this.vetService.saveVet(vet);
			return SUCCESSFUL_REDIRECT_VIEW;
		}
	}

	@ModelAttribute("specialties")
	public Collection<Specialty> populateSpecialities(){
		return this.vetService.findSpecialties();
	}

	@GetMapping(value = { "/vets.xml"})
	public @ResponseBody Vets showResourcesVetList() {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for JSon/Object mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		return vets;
	}

	@GetMapping(value = "/vets/{vetId}/edit")
	public String initUpdateVetForm(@PathVariable("vetId") int vetId, Model model) {
		Vet vet = this.vetService.findVetById(vetId);
		model.addAttribute(vet);
		return VIEWS_VET_CREATE_OR_UPDATE_FORM;

	}

	@PostMapping(value = "/vets/{vetId}/edit")
	public String processUpdateForm(@Valid Vet vet, @PathVariable("vetId") int vetId, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.put("vet", vet);
			return VIEWS_VET_CREATE_OR_UPDATE_FORM;
		}	else {
			vet.setId(vetId);
			this.vetService.saveVet(vet);
			return SUCCESSFUL_REDIRECT_VIEW;
		}

	}

	@GetMapping(value= "/vets/{vetId}/delete")
	public String delete(@PathVariable("vetId") int vetId, ModelMap model) {
		Vet vet = this.vetService.findVetById(vetId);
		this.vetService.deleteVet(vet);
		return SUCCESSFUL_REDIRECT_VIEW;
	}

}
