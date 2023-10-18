package com.lmsf.org.repository;

import com.lmsf.org.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Override
    boolean existsById(Long id);
}
