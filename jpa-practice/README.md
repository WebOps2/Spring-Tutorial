# JPA Practice: Library

A small Spring Data JPA sandbox. There is no web layer, so you practice entities, relationships and queries through **tests** and by reading the **SQL Hibernate prints**.

Stack: Spring Boot 4.1.1, Java 21, Spring Data JPA, H2 (in memory). The database is rebuilt and re-seeded on every run.

## Run it

```powershell
.\mvnw.cmd test                # run all tests
.\mvnw.cmd spring-boot:run     # start the app, seed data, print SQL
```

## What is already built (worked examples)

| File | Shows |
|---|---|
| `domain/Author.java` | `@OneToMany(mappedBy)`, cascade, `orphanRemoval`, helper methods |
| `domain/Book.java` | `@ManyToOne(fetch = LAZY)`, `@JoinColumn`, owning side, `BigDecimal` column |
| `repository/*Repository.java` | derived queries from method names |
| `DataSeeder.java` | cascade save of authors and books |
| `AuthorBookRelationshipTest.java` | how to test with `@DataJpaTest`, `flush()` and `clear()` |

```text
Author 1 ────── many Books      (books.author_id -> authors.id)
```

Before starting, run `.\mvnw.cmd test`, then read the SQL in the output next to each test.

## Exercises

Do them in order. Write a test for each one in `src/test/java/.../repository/`, copying the style of `AuthorBookRelationshipTest`. After each, read the generated SQL and check it is what you expected.

### 1. Many-to-many: Categories
A book has many categories and a category has many books (`Fantasy`, `Classic`, `Dystopian`).
- Create `Category` and a `@ManyToMany` with `@JoinTable(name = "book_categories")`.
- Decide which side owns the relationship, and add `addCategory()` / `removeCategory()` helpers on it.
- Test: add categories to a book, reload it, and check the `book_categories` rows.
- Question: what happens to the join table when you delete a book? A category?

### 2. One-to-many with cascade: Reviews
A book has many reviews (rating 1-5, text, created time).
- Add `Review` with `@ManyToOne` to `Book`.
- Use `cascade = ALL` and `orphanRemoval = true` on `Book.reviews`.
- Test: deleting a book deletes its reviews. Removing one review from the list deletes only that row.
- Add `@Min(1)` / `@Max(5)` (or a database check) and test an invalid rating.

### 3. One-to-one: Author profile
Each author has one optional `AuthorProfile` (biography, birth year, website).
- Use `@OneToOne` with `@MapsId` or a unique foreign key.
- Question: which side should own the foreign key? What is the difference between `@MapsId` and a separate `profile_id` column?

### 4. Members and Loans (two relationships and a join entity)
A member borrows books. A loan records who borrowed which book, when, and when it was returned.
- Entities: `Member`, `Loan` (`@ManyToOne` to both `Member` and `Book`, plus `borrowedAt`, `dueDate`, `returnedAt`).
- Why use a `Loan` entity instead of a `@ManyToMany` between `Member` and `Book`? (Hint: the extra columns.)
- Rule to enforce: a book cannot be loaned twice at the same time. Try it in the service-level code, then think about what the database could enforce.

### 5. Derived queries
Add to `BookRepository` and test each one:
- Title contains a word (case-insensitive)
- Books by author name
- Books in a category (after exercise 1)
- Top 3 most expensive books (`findTop3By...`)
- Whether a book exists with a given ISBN (`existsBy...`)
- Count of books per author (hint: needs `@Query`)

### 6. N+1 queries and fetch joins
1. Write a test that calls `authorRepository.findAll()` and then `author.getBooks().size()` for each author. Count the SQL statements in the output: that is the N+1 problem.
2. Fix it with `@Query("select distinct a from Author a join fetch a.books")` or `@EntityGraph`.
3. Compare the statement counts. Explain why `join fetch` and `Pageable` don't work well together on collections.

### 7. `@Query`, projections and pagination
- Write a JPQL query for authors who have written more than 2 books.
- Return a projection (an interface or a record) with only the author name and book count.
- Use `Pageable` and `Sort` to return books page by page, sorted by price.

### 8. Specifications for dynamic filters
Build a search where every filter is optional: title, author, year range, maximum price and category.
- Make `BookRepository` also extend `JpaSpecificationExecutor<Book>`.
- Write one small `Specification` per filter and combine them with `.and()`.

### 9. Optimistic locking
- Add `@Version private Long version;` to `Book`.
- Write a test that loads the same book in two `EntityManager`s (or two transactions), changes both, and expects an `OptimisticLockingFailureException` on the second save.

### 10. Auditing
- Add `createdAt` and `updatedAt` to your entities with `@CreatedDate`, `@LastModifiedDate`, `@EntityListeners(AuditingEntityListener.class)` and `@EnableJpaAuditing`.

### 11. Switch to PostgreSQL and Flyway
- Add the PostgreSQL driver and a `docker-compose.yml`.
- Replace `ddl-auto=create-drop` with Flyway migrations and `ddl-auto=validate`.
- Run the same tests against PostgreSQL with Testcontainers.

## Tips

- Always change **both sides** of a bidirectional relationship, using the helper methods.
- Keep `@ManyToOne` and `@OneToMany` **lazy**. Fetch what you need explicitly.
- In tests, call `entityManager.flush()` to run the SQL and `entityManager.clear()` so the next read hits the database.
- Don't use Lombok `@Data` or `@EqualsAndHashCode` on entities.
- Use `jakarta.persistence`, not `javax.persistence`.

## Resources

- [Spring Data JPA reference](https://docs.spring.io/spring-data/jpa/reference/)
- [Hibernate ORM associations guide](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#associations)
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
- [Vlad Mihalcea's blog](https://vladmihalcea.com/tutorials/hibernate/) for relationship and performance patterns
