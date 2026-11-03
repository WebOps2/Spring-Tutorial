package com.example.jpapractice.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpapractice.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByAuthorId(Long authorId);

    List<Book> findByPublishedYearBetween(int fromYear, int toYear);

    List<Book> findByPriceLessThanOrderByPriceAsc(BigDecimal maxPrice);

    // TODO (exercise 5): add more filters here (title search, author name, ...).
}
