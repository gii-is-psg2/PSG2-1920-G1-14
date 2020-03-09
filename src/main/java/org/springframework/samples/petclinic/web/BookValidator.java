package org.springframework.samples.petclinic.web;


import org.springframework.samples.petclinic.model.Book;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BookValidator implements Validator{

	private static final String REQUIRED = "required";
	private static final String DATE = "date";
	
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

	}
}
