package com.lmsf.org.repository;

import com.lmsf.org.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Override
    boolean existsById(Long id);
    boolean existsByTitleAndPublicationYear(String title, int publicationYear);
    boolean existsByAuthorId(Long id);
    boolean existsByGenresId(Long id);
    Page<Book> findByGenresIdOrderById(Long genreId, Pageable pageable);
    List<Book> findByGenresId(Long id);
    Page<Book> findByAuthorIdOrderById(Long id, Pageable pageable);
    Page<Book> findByTitleOrderById(String name, Pageable pageable);
    Page<Book> findByPublicationYearOrderById(int year, Pageable pageable);
    Page<Book> findByTitleAndPublicationYearOrderById(String title, int publicationYear, Pageable pageable);
}
