package com.lmsf.org.repository;

import com.lmsf.org.entity.IssuedBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssuedBookRepository extends JpaRepository<IssuedBook, Long> {
}