package bg.softuni._12_springdatabookshopsystem.services;

import bg.softuni._12_springdatabookshopsystem.entities.Author;
import bg.softuni._12_springdatabookshopsystem.entities.Book;
import bg.softuni._12_springdatabookshopsystem.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void storeBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Set<Book> getBooksByReleaseDateAfter(LocalDate releaseDate) {
        return bookRepository.getBooksByReleaseDateAfter(releaseDate);
    }

    @Override
    public Set<Book> getAllBooksByAuthorOrderByReleaseDateDescAndTitleAsc(Author author) {
        return bookRepository.getAllByAuthorOrderByReleaseDateDescTitleAsc(author);
    }
}
