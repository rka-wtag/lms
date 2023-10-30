package com.lmsf.org.repository;

import com.lmsf.org.entity.IssuedBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuedBookRepository extends JpaRepository<IssuedBook, Long> {
    Page<IssuedBook> findByUserId(Long user_id, Pageable pageable);

    boolean existsById(Long id);
    boolean existsByBookId(Long id);
    boolean existsByBookIdAndUserId(Long book_id, Long user_id);
}
