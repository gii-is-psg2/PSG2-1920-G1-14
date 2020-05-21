
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.springframework.samples.petclinic.model.Book;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BookValidator implements Validator {

	private static final String	REQUIRED		= "required";
	private static final String	DATE			= "date";
	private static final String	PAST_DATE		= "pastDate";
	private static final String	START_FIELD		= "start";
	private static final String	FINISH_FIELD	= "finish";


	@Override
	public boolean supports(final Class<?> clazz) {
		return Book.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		Book book = (Book) target;

		if (book.getPet() == null) {
			errors.rejectValue("pet", BookValidator.REQUIRED, BookValidator.REQUIRED);
		}
		if (book.getDetails() == "") {
			errors.rejectValue("details", BookValidator.REQUIRED, BookValidator.REQUIRED);
		}
		if (book.getStart() == null) {
			errors.rejectValue(BookValidator.START_FIELD, BookValidator.REQUIRED, BookValidator.REQUIRED);
		}
		if (book.getFinish() == null) {
			errors.rejectValue(BookValidator.FINISH_FIELD, BookValidator.REQUIRED, BookValidator.REQUIRED);
		}
		if (!errors.hasErrors() && book.getStart().isAfter(book.getFinish())) {
			errors.rejectValue(BookValidator.START_FIELD, BookValidator.DATE, BookValidator.DATE);
		}
		if (!errors.hasErrors() && book.getStart().isBefore(LocalDate.now())) {
			errors.rejectValue(BookValidator.START_FIELD, BookValidator.PAST_DATE, BookValidator.PAST_DATE);
		}
	}
}
