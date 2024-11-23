package com.example.springintro;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.interfaces.AuthorInfo;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
        findTitlesByAgeRestriction();
        findAllTitlesGoldenEditionsLess5000copies();
        findAllTitlesAndPricesLessThan5AndHigherThan40();
        findAllTitlesNotReleasedInAGivenYear();
        findAllBooksReleasedBeforeDate();
        findAllAuthorsByFirstNameEnds();
        findAllTitlesThatContainsString();
        findAllTitlesByAuthorFirstNameStarts();
        findBooksCountByTitleLength();
        findAllAuthorsOrderByTotalCopiesByJava();
        findAllAuthorsOrderByTotalCopiesByProjection();
        findAllAuthorsOrderByTotalCopiesByInterface();
        updateBookCopiesByReleaseDate();
        deleteBooksWithCopiesLessThan();
    }

    private void deleteBooksWithCopiesLessThan() throws IOException {
        int copies = Integer.parseInt(bufferedReader.readLine());
        int deleted = bookService.deleteBooksWithCopiesLessThan(copies);
        System.out.println(deleted);
    }

    private void updateBookCopiesByReleaseDate() throws IOException {
        String date = bufferedReader.readLine();
        int copies = Integer.parseInt(bufferedReader.readLine());
        int count = bookService.updateBookCopiesByReleaseDate(date, copies);
        System.out.println(count);
    }

    private void findAllAuthorsOrderByTotalCopiesByJava() {
        List<String> allAuthorsOrderByTotalCopies = authorService.findAllAuthorsOrderByTotalCopiesByJava();
        System.out.println(allAuthorsOrderByTotalCopies);
    }

    private void findAllAuthorsOrderByTotalCopiesByInterface() {
        List<AuthorInfo> allAuthorsOrderByTotalCopiesByInterface =
                authorService.findAllAuthorsOrderByTotalCopiesByInterface();
        allAuthorsOrderByTotalCopiesByInterface.forEach(a -> {
            System.out.printf("%s %s - %d%n", a.getFirstName(), a.getLastName(), a.getCopies());
        });
    }

    private void findAllAuthorsOrderByTotalCopiesByProjection() {
        List<String> allAuthorsOrderByTotalCopies = authorService.findAllAuthorsOrderByTotalCopies();
        System.out.println(allAuthorsOrderByTotalCopies);
    }

    private void findBooksCountByTitleLength() throws IOException {
        int length = Integer.parseInt(bufferedReader.readLine());
        int count = bookService.findBooksCountByTitleLength(length);
        System.out.println(count);
    }

    private void findAllTitlesByAuthorFirstNameStarts() throws IOException {
        String starts = bufferedReader.readLine();
        List<String> allTitlesByAuthorFirstNameStarts = bookService.findAllTitlesByAuthorFirstNameStarts(starts);
        System.out.println(allTitlesByAuthorFirstNameStarts);
    }

    private void findAllTitlesThatContainsString() throws IOException {
        String contains = bufferedReader.readLine();
        List<String> titles = bookService.findAllTitlesThatContains(contains);
        System.out.println(titles);
    }

    private void findAllAuthorsByFirstNameEnds() throws IOException {
        String ending = bufferedReader.readLine();
        List<String> authors = authorService.findAllAuthorsByFirstNameEnds(ending);
        System.out.println(authors);
    }

    private void findAllBooksReleasedBeforeDate() throws IOException {
        LocalDate date = LocalDate.parse(bufferedReader.readLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        List<Book> books = bookService.findAllBooksReleasedBefore(date);
        books.forEach(b -> System.out.printf("%s %s %.2f%n", b.getTitle(), b.getEditionType().name(), b.getPrice()));
    }

    private void findAllTitlesNotReleasedInAGivenYear() throws IOException {
        int year = Integer.parseInt(bufferedReader.readLine());
        List<String> bookTitlesNotReleasedInYear = bookService.findBookTitlesNotReleasedIn(year);
        System.out.println(bookTitlesNotReleasedInYear);
    }

    private void findAllTitlesAndPricesLessThan5AndHigherThan40() {
        List<String> bookTitlesPriceOutOfRange = bookService
                .findBookTitlesPriceOutOfRange(BigDecimal.valueOf(5.0), BigDecimal.valueOf(40.0));
        System.out.println(bookTitlesPriceOutOfRange);
    }

    private void findAllTitlesGoldenEditionsLess5000copies() {
        List<String> goldenBookTitles = bookService.findGoldenBookTitles();
        System.out.println(goldenBookTitles);
    }

    private void findTitlesByAgeRestriction() throws IOException {
        String input = bufferedReader.readLine();
        List<String> allTitlesByAgeRestriction = bookService.findAllTitlesByAgeRestriction(input);
        System.out.println(allTitlesByAgeRestriction);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
