package bg.softuni._12_springdatabookshopsystem.services;

import bg.softuni._12_springdatabookshopsystem.entities.Author;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface AuthorService {
    Author getRandomAuthor();
    void storeAuthor(Author author);
    Set<Author> getAuthorsByBooksReleaseDateBefore(LocalDate releaseDate);
    List<Author> getAllAuthorsOrderByBookCount();
    Author getAuthorByFullName(String firstName, String lastName);
}
