package com.example.springintro.service;

import com.example.springintro.model.entity.Book;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllTitlesByAgeRestriction(String input);

    List<String> findGoldenBookTitles();

    List<String> findBookTitlesPriceOutOfRange(BigDecimal lower, BigDecimal upper);

    List<String> findBookTitlesNotReleasedIn(int year);

    List<Book> findAllBooksReleasedBefore(LocalDate date);

    List<String> findAllTitlesThatContains(String contains);

    List<String> findAllTitlesByAuthorFirstNameStarts(String starts);

    int findBooksCountByTitleLength(int length);

    int updateBookCopiesByReleaseDate(String date, int copies);

    int deleteBooksWithCopiesLessThan(int copies);
}
