package com.lmsf.org.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSearchRequestDto {
    private String title;
    private int publicationYear;
    private int pageNo;
    private int pageSize;
    private String sortingField;
}
