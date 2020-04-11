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
        return "pets/createOrUpdateBookForm";
    }

    @InitBinder("book")
	public void initBookBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new BookValidator());
	}

	@PostMapping(value = "/owners/{ownerId}/pets/{petId}/books/new")
	public String processNewBookForm(@Valid Book book, BindingResult result) {
		if (result.hasErrors()) {
			return "pets/createOrUpdateBookForm";
		} else {
			try {
				this.clinicService.saveBooking(book);
			} catch (TotalOverlapDateException e) {
				result.rejectValue("start", "completeOverlap","completeOverlap");
				return "pets/createOrUpdateBookForm";
			} catch (PartialOverlapDateException e) {
				result.rejectValue("start", "partialOverlap","partialOverlap");
                result.rejectValue("finish", "partialOverlap","partialOverlap");
				return "pets/createOrUpdateBookForm";
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
