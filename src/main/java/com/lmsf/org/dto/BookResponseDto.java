package com.lmsf.org.dto;

import com.lmsf.org.entity.Author;
import com.lmsf.org.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDto {
    private Long id;
    private String title;
    private int publicationYear;
    private int copiesAvailable;
    private Author author;
    private Set<Genre> genres = new HashSet<>();

}
