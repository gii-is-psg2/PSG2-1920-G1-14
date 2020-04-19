package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Book;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.exceptions.PartialOverlapDateException;
import org.springframework.samples.petclinic.service.exceptions.TotalOverlapDateException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class BookController {

    private final ClinicService clinicService;

    private static final String CREATE_OR_UPDATE_BOOK_VIEW = "pets/createOrUpdateBookForm";
    private static final String OVERLAP_PARTIAL = "partialOverlap";
    private static final String OVERLAP_COMPLETE = "completeOverlap";

    @Autowired
    public BookController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("book")
    public Book loadPetWithBook(@PathVariable("petId") int petId) {
        Pet pet = this.clinicService.findPetById(petId);
        Book book = new Book();
        pet.addBooking(book);
        return book;
    }

    // Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is called
    @GetMapping(value = "/owners/*/pets/{petId}/books/new")
    public String initNewBookForm(@PathVariable("petId") int petId, Map<String, Object> model) {
        return CREATE_OR_UPDATE_BOOK_VIEW;
    }

    @InitBinder("book")
	public void initBookBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new BookValidator());
	}

	@PostMapping(value = "/owners/{ownerId}/pets/{petId}/books/new")
	public String processNewBookForm(@Valid Book book, BindingResult result) {
		if (result.hasErrors()) {
			return CREATE_OR_UPDATE_BOOK_VIEW;
		} else {
			try {
				this.clinicService.saveBooking(book);
			} catch (TotalOverlapDateException e) {
				result.rejectValue("start", OVERLAP_COMPLETE,OVERLAP_COMPLETE);
				return CREATE_OR_UPDATE_BOOK_VIEW;
			} catch (PartialOverlapDateException e) {
				result.rejectValue("start", OVERLAP_PARTIAL,OVERLAP_PARTIAL);
                result.rejectValue("finish", OVERLAP_PARTIAL,OVERLAP_PARTIAL);
				return CREATE_OR_UPDATE_BOOK_VIEW;
			}
			return "redirect:/owners/{ownerId}";
		}
	}

    @GetMapping(value= "/owners/{ownerId}/pets/{petId}/books/{bookId}/delete")
    public String delete(@PathVariable("bookId") int bookId, @PathVariable("petId") int petId, ModelMap model) {
        Book book = this.clinicService.findBookById(bookId);
        Pet pet = this.clinicService.findPetById(petId);

        pet.deleteBook((Book) model.getAttribute("book"));
        pet.deleteBook(book);

        this.clinicService.savePet(pet);
        this.clinicService.deleteBook(book);
        return "redirect:/owners/{ownerId}";
    }
}
