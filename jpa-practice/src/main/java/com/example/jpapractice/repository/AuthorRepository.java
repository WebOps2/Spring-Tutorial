package com.example.jpapractice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpapractice.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    // Derived query: Spring Data builds the SQL from the method name.
    List<Author> findByNameContainingIgnoreCase(String namePart);

    // TODO (exercise 6): add a query that loads authors together with their books in ONE query.
}
