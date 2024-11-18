package bg.softuni._12_springdatabookshopsystem.repositories;

import bg.softuni._12_springdatabookshopsystem.entities.Author;
import bg.softuni._12_springdatabookshopsystem.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Set<Book> getBooksByReleaseDateAfter(LocalDate releaseDate);
    Set<Book> getAllByAuthorOrderByReleaseDateDescTitleAsc(Author author);
}
