package com.lmsf.org.repository;

import com.lmsf.org.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface BookRepository extends JpaRepository<Book, Long> {
    Set<Book> findByGenresId(Long genreId);
    Set<Book> findByAuthorId(Long id);
    Set<Book> findByTitle(String name);
    Set<Book> findByPublicationYear(int year);

}
