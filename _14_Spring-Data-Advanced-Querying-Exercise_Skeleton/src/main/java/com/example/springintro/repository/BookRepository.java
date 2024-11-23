package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lower, BigDecimal upper);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate releaseDateBefore, LocalDate releaseDateAfter);

    List<Book> findAllByTitleContainingIgnoreCase(String contains);

    List<Book> findAllByAuthorFirstNameStartingWithIgnoreCase(String lastName);

    @Query("SELECT COUNT(*) FROM Book b WHERE LENGTH(b.title) > :length")
    int findBooksCountByTitleLength(int length);

    @Query("UPDATE Book b SET b.copies = b.copies + :copies WHERE b.releaseDate > :localDate")
    @Modifying
    @Transactional
    int updateBookCopiesByReleaseDate(LocalDate localDate, int copies);

    @Transactional
    int deleteBooksByCopiesLessThan(int copies);
}
