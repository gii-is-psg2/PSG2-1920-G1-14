package org.springframework.samples.petclinic.web;


import java.time.LocalDate;

import org.springframework.samples.petclinic.model.Book;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BookValidator implements Validator{

	private static final String REQUIRED = "required";
	private static final String DATE = "date";
	private static final String PAST_DATE = "pastDate";


	@Override
	public boolean supports(Class<?> clazz) {
		return Book.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Book book = (Book) target;

		if (book.getPet() == null) {
			errors.rejectValue("pet", REQUIRED, REQUIRED);
		}


		if (book.getStart().isAfter(book.getFinish())) {
			errors.rejectValue("start", DATE, DATE);

		}

		if(book.getStart().isBefore(LocalDate.now())) {
			errors.rejectValue("start", PAST_DATE, PAST_DATE);
		}

	}
}
