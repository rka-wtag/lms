package com.lmsf.org.repository;

import com.lmsf.org.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Override
    boolean existsById(Long id);
    boolean existsByTitleAndPublicationYear(String title, int publicationYear);
    List<Book> findByGenresIdOrderById(Long genreId);
    List<Book> findByAuthorIdOrderById(Long id);
    List<Book> findByTitleOrderById(String name);
    List<Book> findByPublicationYearOrderById(int year);
    List<Book> findByTitleAndPublicationYearOrderById(String title, int publicationYear);

}
