package com.lmsf.org.repository;

import com.lmsf.org.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Override
    boolean existsById(Long id);
    boolean existsByTitleAndPublicationYear(String title, int publicationYear);
    boolean existsByAuthorId(Long id);
    boolean existsByGenresId(Long id);
    Page<Book> findByGenresId(Long genreId, Pageable pageable);
    Page<Book> findByAuthorId(Long id, Pageable pageable);
    Page<Book> findByTitle(String name, Pageable pageable);
    Page<Book> findByPublicationYear(int year, Pageable pageable);
    Page<Book> findByTitleAndPublicationYear(String title, int publicationYear, Pageable pageable);
}
