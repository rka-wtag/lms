package com.lmsf.org.repository;

import com.lmsf.org.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Override
    boolean existsById(Long id);
    boolean existsByEmail(String email);
}
