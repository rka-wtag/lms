package com.lmsf.org.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorRequestDto {
    @NotNull(message = "The author should have last name")
    @Size(min = 1, max = 255, message = "The author should have last name")
    private String lastName;

    @NotNull(message = "The author should have first name")
    @Size(min = 1, max = 255, message = "The author should have first name")
    private String firstName;

    @NotNull(message = "The author should have an email")
    @Email(message = "Invalid email address")
    @Size(min = 1, max = 255, message = "The author should have an email")
    @Column(unique = true)
    private String email;
}
