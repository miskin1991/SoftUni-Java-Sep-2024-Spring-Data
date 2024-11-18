package bg.softuni._12_springdatabookshopsystem;

import bg.softuni._12_springdatabookshopsystem.entities.*;
import bg.softuni._12_springdatabookshopsystem.services.AuthorService;
import bg.softuni._12_springdatabookshopsystem.services.BookService;
import bg.softuni._12_springdatabookshopsystem.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    public CommandLineRunnerImpl(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedAuthors();
        seedCategories();
        seedBooks();
        bookService.getBooksByReleaseDateAfter(LocalDate.of(2000, 1, 1))
                .forEach(book -> System.out.printf("%s%n", book.getTitle()));
        authorService.getAuthorsByBooksReleaseDateBefore(LocalDate.of(1990, 1, 1))
                .forEach(author -> System.out.printf("%s %s%n", author.getFirstName(), author.getLastName()));
        authorService.getAllAuthorsOrderByBookCount()
                .forEach(author -> System.out.printf("%s %s %d%n",
                        author.getFirstName(), author.getLastName(), author.getBooks().size()));
        bookService.getAllBooksByAuthorOrderByReleaseDateDescAndTitleAsc(
                authorService.getAuthorByFullName("George", "Powell"))
                .forEach(book -> System.out.printf("%s %s %d %n",
                        book.getTitle(), book.getReleaseDate().toString(), book.getCopies()));
    }

    private void seedBooks() throws IOException {
        Files.readAllLines(Path.of("src/main/resources/data/books.txt"))
                .forEach(row -> {
                    String[] data = row.split("\\s+");

                    Author author = authorService.getRandomAuthor();
                    EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];
                    LocalDate date = LocalDate.parse(data[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
                    int copies = Integer.parseInt(data[2]);
                    BigDecimal price = new BigDecimal(data[3]);
                    AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(data[4])];
                    String title = Arrays.stream(data).skip(5).collect(Collectors.joining(" "));

                    Set<Category> categories = categoryService.getRandomCategory();

                    Book book = new Book();
                    book.setTitle(title);
                    book.setEditionType(editionType);
                    book.setPrice(price);
                    book.setReleaseDate(date);
                    book.setAgeRestriction(ageRestriction);
                    book.setAuthor(author);
                    book.setCategories(categories);
                    book.setCopies(copies);

                    bookService.storeBook(book);
                });
    }

    private void seedCategories() throws IOException {
        Files.readAllLines(Path.of("src/main/resources/data/categories.txt"))
                .forEach(row -> {
                    String[] data = row.split("\\s+");

                    Category category = new Category();
                    category.setName(data[0]);
                    try {
                        categoryService.storeCategory(category);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                });
    }

    private void seedAuthors() throws IOException {
        Files.readAllLines(Path.of("src/main/resources/data/authors.txt"))
                .forEach(row -> {
                    String[] data = row.split("\\s+");

                    Author author = new Author();
                    author.setFirstName(data[0]);
                    author.setLastName(data[1]);
                    authorService.storeAuthor(author);
                });
    }
}
