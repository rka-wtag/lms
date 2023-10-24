package com.lmsf.org.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponseDto {
    private Long id;
    private String lastName;
    private String firstName;
    private String email;
}
