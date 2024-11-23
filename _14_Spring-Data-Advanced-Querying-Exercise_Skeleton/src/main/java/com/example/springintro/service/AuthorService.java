package com.example.springintro.service;

import com.example.springintro.model.entity.Author;
import com.example.springintro.model.interfaces.AuthorInfo;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<String> getAllAuthorsOrderByCountOfTheirBooks();

    List<String> findAllAuthorsByFirstNameEnds(String ending);

    List<String> findAllAuthorsOrderByTotalCopies();

    List<String> findAllAuthorsOrderByTotalCopiesByJava();

    List<AuthorInfo> findAllAuthorsOrderByTotalCopiesByInterface();
}
