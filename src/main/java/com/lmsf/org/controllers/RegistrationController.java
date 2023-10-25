package com.lmsf.org.controllers;

import com.lmsf.org.dto.RegisterDto;
import com.lmsf.org.exception.ConstraintsViolationException;
import com.lmsf.org.repository.UserRepository;
import com.lmsf.org.service.RegistrationService;
import com.lmsf.org.dto.RegistrationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRepository userRepository;
    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<RegistrationResponseDto> signUp(@RequestBody @Valid RegisterDto registerDto){
        registrationService.register(registerDto);
        return ResponseEntity.ok(new RegistrationResponseDto("Registration successful"));
    }
}
