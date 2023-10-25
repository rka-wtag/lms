package com.lmsf.org.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class IssueResponseDto {
    private Long id;
    private UserResponseDto issuer;
    private BookResponseDto book;

}
