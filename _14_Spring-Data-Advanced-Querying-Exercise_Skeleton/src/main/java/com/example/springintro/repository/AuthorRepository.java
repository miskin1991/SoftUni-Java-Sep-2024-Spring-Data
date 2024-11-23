package com.example.springintro.repository;

import com.example.springintro.model.entity.Author;
import com.example.springintro.model.interfaces.AuthorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a ORDER BY SIZE(a.books) DESC")
    List<Author> findAllByBooksSizeDESC();

    List<Author> findAllByFirstNameEndingWith(String ending);

    @Query("SELECT b.author.firstName, b.author.lastName, SUM(b.copies) FROM" +
            " Book b GROUP BY b.author ORDER BY SUM(b.copies) DESC")
    List<Object> findAllAuthorsOrderByTotalCopies();

    @Query("SELECT b.author.firstName as firstName," +
            " b.author.lastName as lastName," +
            " SUM(b.copies) as copies" +
            " FROM Book b GROUP BY b.author" +
            " ORDER BY copies DESC")
    List<AuthorInfo> findAllAuthorsInfoOrderByTotalCopies();
}
