package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Book;
import org.springframework.samples.petclinic.repository.BookRepository;
import org.springframework.samples.petclinic.service.exceptions.PartialOverlapDateException;
import org.springframework.samples.petclinic.service.exceptions.TotalOverlapDateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Collection<Book> findBookingsByPetOwnerId(final int ownerId) {
        return this.bookRepository.findByPetOwnerId(ownerId);
    }

    @Transactional(readOnly = true)
    public Book findBookById(final int id) {
        return this.bookRepository.findById(id);
    }

    public void saveBooking(Book book) throws TotalOverlapDateException, PartialOverlapDateException {
        Collection<Book> books = bookRepository.findByPetOwnerId(book.getPet().getId());
        Collection<Book> bookStart = books;
        Collection<Book> bookFinish = books;

        // CAMBIAR LAMBDA EXPRESION
        List<LocalDate> startDates = bookStart.stream().map(Book::getStart).collect(Collectors.toList());
        List<LocalDate> finishDates = bookFinish.stream().map(Book::getFinish).collect(Collectors.toList());

        String overlapType = overlapType(startDates, finishDates, book.getStart(), book.getFinish());
        if (!overlapType.equals("")) {
            if (overlapType.equals("complete")) {
                throw new TotalOverlapDateException();
            } else {
                throw new PartialOverlapDateException();
            }
        } else {
            bookRepository.save(book);
        }
    }

    private String overlapType(List<LocalDate> startDates, List<LocalDate> finishDates, LocalDate newBookStart,
                               LocalDate newBookFinish) {
        int i = 0;
        String res = "";
        String partial = "partial";
        String complete = "complete";
        while (i < startDates.size()) {
            LocalDate startBookI = startDates.get(i);
            LocalDate finishBookI = finishDates.get(i);
            if (((newBookStart.equals(startBookI) || newBookStart.isAfter(startBookI))
                && ((newBookFinish.equals(finishBookI)) || newBookFinish.isBefore(finishBookI)))
                || (newBookStart.equals(startBookI) || newBookStart.isBefore(startBookI))
                && ((newBookFinish.equals(finishBookI)) || newBookFinish.isAfter(finishBookI))) {
                res = complete;
            } else if (startBookI.isBefore(newBookFinish)
                && (finishBookI.isAfter(newBookFinish) || finishBookI.equals(newBookFinish))) {
                res = partial;
            } else if (startBookI.isBefore(newBookStart) && finishBookI.isAfter(newBookStart)) {
                res = partial;
            } else if (startBookI.equals(newBookFinish) || finishBookI.equals(newBookStart)) {
                res = partial;
            }
            i++;
        }
        return res;
    }

    @Transactional
    public void deleteBook(final Book book) {
        this.bookRepository.delete(book);
    }
}
