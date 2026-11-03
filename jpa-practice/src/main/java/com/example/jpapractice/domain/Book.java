package com.example.jpapractice.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * WORKED EXAMPLE: the "many" side of a one-to-many relationship (many-to-one from Book's view).
 *
 * This side owns the foreign key: books.author_id -> authors.id.
 */
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    @Column(nullable = false)
    private int publishedYear;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    // LAZY: the Author row is only loaded when you call getAuthor().getXxx().
    // (@ManyToOne is EAGER by default, which is a common source of extra queries.)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    protected Book() {
        // required by JPA
    }

    public Book(String title, String isbn, int publishedYear, BigDecimal price) {
        this.title = title;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    void setAuthor(Author author) {
        this.author = author;
    }
}
