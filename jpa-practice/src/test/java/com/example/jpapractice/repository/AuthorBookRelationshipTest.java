package com.example.jpapractice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.example.jpapractice.domain.Author;
import com.example.jpapractice.domain.Book;

import jakarta.persistence.EntityManager;

/**
 * WORKED EXAMPLES. Read these first, then copy the style for your own exercises.
 *
 * @DataJpaTest starts only the JPA layer with an in-memory database and rolls back each test.
 * Hibernate batches SQL until a flush, so tests call flush() and clear() to force real
 * SQL and to make the next read hit the database instead of the first-level cache.
 */
@DataJpaTest
class AuthorBookRelationshipTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void savingAuthorCascadesToBooks() {
        Author author = new Author("Ursula K. Le Guin");
        author.addBook(new Book("A Wizard of Earthsea", "978-0547773742", 1968, new BigDecimal("8.99")));

        authorRepository.save(author);
        entityManager.flush();
        entityManager.clear();

        assertThat(bookRepository.findByIsbn("978-0547773742")).isPresent();
    }

    @Test
    void booksKnowTheirAuthorThroughTheForeignKey() {
        Author author = new Author("Octavia E. Butler");
        author.addBook(new Book("Kindred", "978-0807083697", 1979, new BigDecimal("10.99")));
        author.addBook(new Book("Parable of the Sower", "978-0446675505", 1993, new BigDecimal("11.99")));
        authorRepository.save(author);
        entityManager.flush();
        entityManager.clear();

        List<Book> books = bookRepository.findByAuthorId(author.getId());

        assertThat(books).extracting(Book::getTitle)
                .containsExactlyInAnyOrder("Kindred", "Parable of the Sower");
    }

    @Test
    void removingABookFromTheAuthorDeletesTheRow() {
        Author author = new Author("Test Author");
        Book book = new Book("Temporary", "000-0000000001", 2000, new BigDecimal("1.00"));
        author.addBook(book);
        authorRepository.save(author);
        entityManager.flush();

        author.removeBook(book); // orphanRemoval = true deletes the row
        entityManager.flush();
        entityManager.clear();

        assertThat(bookRepository.findByIsbn("000-0000000001")).isEmpty();
    }

    @Test
    void derivedQueryFiltersByYearRange() {
        Author author = new Author("Range Author");
        author.addBook(new Book("Old", "000-0000000002", 1900, new BigDecimal("5.00")));
        author.addBook(new Book("Newer", "000-0000000003", 1990, new BigDecimal("5.00")));
        authorRepository.save(author);
        entityManager.flush();
        entityManager.clear();

        List<Book> result = bookRepository.findByPublishedYearBetween(1950, 2000);

        assertThat(result).extracting(Book::getTitle).containsExactly("Newer");
    }
}
