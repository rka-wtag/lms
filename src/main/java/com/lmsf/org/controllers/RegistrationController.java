package com.lmsf.org.controllers;

import com.lmsf.org.dto.RegisterDto;
import com.lmsf.org.dto.RegistrationResponseDto;
import com.lmsf.org.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<RegistrationResponseDto> signUp(@RequestBody @Valid RegisterDto registerDto){
        registrationService.register(registerDto);
        return ResponseEntity.ok(new RegistrationResponseDto("Registration successful"));
    }
}
