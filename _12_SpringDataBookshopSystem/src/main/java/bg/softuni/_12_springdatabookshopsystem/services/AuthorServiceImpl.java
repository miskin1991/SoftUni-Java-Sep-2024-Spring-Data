package bg.softuni._12_springdatabookshopsystem.services;

import bg.softuni._12_springdatabookshopsystem.entities.Author;
import bg.softuni._12_springdatabookshopsystem.repositories.AuthorRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getRandomAuthor() {
        long count = authorRepository.count();
        long randomId = (long) ((Math.random() * count) + 1);
        return authorRepository.getReferenceById(randomId);
    }

    @Override
    public void storeAuthor(Author author) {
        if (author.getLastName() == null
                || author.getLastName().isBlank() || author.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Author last name cannot be empty");
        } else {
            authorRepository.save(author);
        }
    }

    @Override
    public Set<Author> getAuthorsByBooksReleaseDateBefore(LocalDate releaseDate) {
        return authorRepository.getAuthorsByBooksReleaseDateBefore(releaseDate);
    }

    @Override
    public List<Author> getAllAuthorsOrderByBookCount() {
        return authorRepository.findAll().stream()
                .sorted((a1, a2) -> a2.getBooks().size() - a1.getBooks().size()).toList();
    }

    @Override
    public Author getAuthorByFullName(String firstName, String lastName) {
        return authorRepository.getAuthorByFirstNameAndLastName(firstName, lastName);
    }
}
