package com.lmsf.org.repository;

import com.lmsf.org.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Override
    boolean existsById(Long id);
    List<Book> findByGenresId(Long genreId);
    List<Book> findByAuthorId(Long id);
    List<Book> findByTitle(String name);
    List<Book> findByPublicationYear(int year);

}
