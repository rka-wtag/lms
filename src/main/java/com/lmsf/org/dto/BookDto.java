package com.lmsf.org.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    @NotNull(message = "Title shouldn't be null")
    @Size(min = 1, max = 255, message = "Title shouldn't be null")
    private String title;
    @Min(1500)
    private int publicationYear;
    private int copiesAvailable;
    @NotNull(message = "The book should have a author")
    private Long authorId;
    @NotNull(message = "The book should have at least one genre")
    private Set<Long> genreIds;
}
