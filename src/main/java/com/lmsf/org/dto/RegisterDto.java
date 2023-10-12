package com.lmsf.org.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotNull(message = "input a valid name")
    @Size(min = 2, max = 255, message = "Enter a valid name")
    private String name;
    @NotNull(message = "input a valid username")
    @Size(min = 1, max = 255, message = "Enter a valid username")
    private String userName;
    @NotNull(message = "Enter password")
    @Size(min = 6, max = 30, message = "password length is too short")
    private String password;
    @NotNull(message = "Enter password")
    @Size(min = 6, max = 30, message = "password length is too short")
    private String confirmPassword;
}
