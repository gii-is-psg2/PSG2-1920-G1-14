
package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.stereotype.Component;

@Component
public class SpecialtyFormatter implements Formatter<Specialty> {

	private VetService vetService;


	@Autowired
	public SpecialtyFormatter(final VetService vetService) {
		this.vetService = vetService;
	}

	@Override
	public Specialty parse(final String text, final Locale locale) throws ParseException {
		Specialty spec = this.vetService.findSpecialtyByName(text);
		if (spec == null) {
			throw new ParseException("The text does not match with any Specialty: " + text, 0);
		}
		return spec;
	}

	@Override
	public String print(final Specialty specialty, final Locale locale) {
		return specialty.getName();
	}
}
