package com.example.jpapractice;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.jpapractice.domain.Author;
import com.example.jpapractice.domain.Book;
import com.example.jpapractice.repository.AuthorRepository;

/**
 * Inserts sample data on startup so you always have something to query.
 * Not used in tests (the "test" profile is excluded), so tests control their own data.
 */
@Component
@Profile("!test")
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final AuthorRepository authorRepository;

    public DataSeeder(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void run(String... args) {
        Author orwell = new Author("George Orwell");
        orwell.addBook(new Book("1984", "978-0451524935", 1949, new BigDecimal("9.99")));
        orwell.addBook(new Book("Animal Farm", "978-0451526342", 1945, new BigDecimal("7.99")));

        Author herbert = new Author("Frank Herbert");
        herbert.addBook(new Book("Dune", "978-0441013593", 1965, new BigDecimal("12.99")));

        Author austen = new Author("Jane Austen");
        austen.addBook(new Book("Pride and Prejudice", "978-0141439518", 1813, new BigDecimal("6.99")));
        austen.addBook(new Book("Emma", "978-0141439587", 1815, new BigDecimal("6.49")));
        austen.addBook(new Book("Persuasion", "978-0141439686", 1817, new BigDecimal("5.99")));

        // cascade = ALL means saving the author also saves its books.
        authorRepository.saveAll(java.util.List.of(orwell, herbert, austen));

        log.info("Seeded {} authors.", authorRepository.count());
    }
}
