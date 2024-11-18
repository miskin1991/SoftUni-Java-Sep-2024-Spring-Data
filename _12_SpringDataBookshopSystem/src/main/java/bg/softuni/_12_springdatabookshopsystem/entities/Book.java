package bg.softuni._12_springdatabookshopsystem.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "age_restriction", nullable = false)
    @Enumerated(EnumType.STRING)
    private AgeRestriction ageRestriction;

    @Basic(optional = false)
    private int copies;

    @Column(columnDefinition = "TEXT(1000)")
    private String description;

    @Column(name = "edition_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EditionType editionType;

    @Basic(optional = false)
    private BigDecimal price;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(length = 50, nullable = false)
    private String title;

    @ManyToOne
    private Author author;

    @ManyToMany
    private Set<Category> categories;

    public Book() {
        this.categories = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AgeRestriction getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(AgeRestriction ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EditionType getEditionType() {
        return editionType;
    }

    public void setEditionType(EditionType editionType) {
        this.editionType = editionType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
