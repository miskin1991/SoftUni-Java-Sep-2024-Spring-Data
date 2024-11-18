package bg.softuni._12_springdatabookshopsystem.services;

import bg.softuni._12_springdatabookshopsystem.entities.Author;
import bg.softuni._12_springdatabookshopsystem.entities.Book;

import java.time.LocalDate;
import java.util.Set;

public interface BookService {
    void storeBook(Book book);
    Set<Book> getBooksByReleaseDateAfter(LocalDate releaseDate);

    Set<Book> getAllBooksByAuthorOrderByReleaseDateDescAndTitleAsc(Author author);
}
