package bg.softuni._12_springdatabookshopsystem.repositories;

import bg.softuni._12_springdatabookshopsystem.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Set<Author> getAuthorsByBooksReleaseDateBefore(LocalDate localDate);
    Author getAuthorByFirstNameAndLastName(String firstName, String lastName);
}
