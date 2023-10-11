package com.lmsf.org.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    @NotNull(message = "Title shouldn't be null")
    @Size(min = 1, max = 255)
    private String title;
    @NotNull(message = "The book should have a publication year")
    private int publication_year;
    private int copies_available;
    @NotNull(message = "The book should have a author")
    private Long author_id;
    @NotNull(message = "The book should have at least one genre")
    private Set<Long> genreIds;
}