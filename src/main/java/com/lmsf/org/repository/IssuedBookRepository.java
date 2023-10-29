package com.lmsf.org.repository;

import com.lmsf.org.entity.IssuedBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssuedBookRepository extends JpaRepository<IssuedBook, Long> {
    List<IssuedBook> findByUserId(Long user_id);
    List<IssuedBook> findByBookId(Long bookId);
    boolean existsById(Long id);
    boolean existsByBookId(Long id);
}
