package com.lmsf.org.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookModel {
    private String title;
    private int publication_year;
    private int copies_available;
    private Long author_id;
    private Set<Long> genreIds;
}
