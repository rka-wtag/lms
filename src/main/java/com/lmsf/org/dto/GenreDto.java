package com.lmsf.org.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {
    @Size(min = 1, max = 255, message = "Inavlid genre name")
    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
