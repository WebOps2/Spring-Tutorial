package com.example.jpapractice.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * WORKED EXAMPLE: the "one" side of a one-to-many relationship.
 *
 * One Author has many Books. The foreign key lives in the books table (books.author_id),
 * so Book OWNS the relationship and Author is the INVERSE side (mappedBy = "author").
 */
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // cascade = ALL: saving/deleting an Author also saves/deletes its books.
    // orphanRemoval = true: removing a book from this list deletes the book row.
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    protected Author() {
        // required by JPA
    }

    public Author(String name) {
        this.name = name;
    }

    // Helper methods keep BOTH sides of the relationship in sync.
    // Always use these instead of books.add(...) directly.
    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.setAuthor(null);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }
}
