package com.example.springintro.service.impl;

import com.example.springintro.model.entity.*;
import com.example.springintro.repository.BookRepository;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
       return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllTitlesByAgeRestriction(String input) {
        List<Book> allByAgeRestriction = bookRepository
                .findAllByAgeRestriction(AgeRestriction.valueOf(input.toUpperCase()));

        return mapBookTitles(allByAgeRestriction);
    }

    @Override
    public List<String> findGoldenBookTitles() {
        List<Book> allByEditionType = bookRepository
                .findAllByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000);

        return mapBookTitles(allByEditionType);
    }

    @Override
    public List<String> findBookTitlesPriceOutOfRange(BigDecimal lower, BigDecimal upper) {
        List<Book> allByPriceLessThanAndPriceGreaterThan = bookRepository
                .findAllByPriceLessThanOrPriceGreaterThan(lower, upper);

        return mapBookTitles(allByPriceLessThanAndPriceGreaterThan);
    }

    @Override
    public List<String> findBookTitlesNotReleasedIn(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        List<Book> allByReleaseDateYearNot = bookRepository
                .findAllByReleaseDateBeforeOrReleaseDateAfter(startDate, endDate);

        return mapBookTitles(allByReleaseDateYearNot);
    }

    @Override
    public List<Book> findAllBooksReleasedBefore(LocalDate date) {
        return bookRepository.findAllByReleaseDateBefore(date);
    }

    @Override
    public List<String> findAllTitlesThatContains(String contains) {
        List<Book> allThatContains = bookRepository.findAllByTitleContainingIgnoreCase(contains);

        return mapBookTitles(allThatContains);
    }

    @Override
    public List<String> findAllTitlesByAuthorFirstNameStarts(String starts) {
        List<Book> allByAuthorLastNameStartingWithIgnoreCase =
                bookRepository.findAllByAuthorFirstNameStartingWithIgnoreCase(starts);

        return mapBookTitles(allByAuthorLastNameStartingWithIgnoreCase);
    }

    @Override
    public int findBooksCountByTitleLength(int length) {
        return bookRepository.findBooksCountByTitleLength(length);
    }

    @Override
    public int updateBookCopiesByReleaseDate(String date, int copies) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd MMM yyyy"));
        return bookRepository.updateBookCopiesByReleaseDate(localDate, copies);
    }

    @Override
    public int deleteBooksWithCopiesLessThan(int copies) {
        return bookRepository.deleteBooksByCopiesLessThan(copies);
    }

    private static List<String> mapBookTitles(List<Book> books) {
        return books
                .stream()
                .map(Book::getTitle)
                .toList();
    }

    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }
}
