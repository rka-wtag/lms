package com.lmsf.org.controllers;

import com.lmsf.org.dto.RegisterDto;
import com.lmsf.org.repository.UserRepository;
import com.lmsf.org.service.RegistrationService;
import com.lmsf.org.util.ResponseRegistration;
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
    public ResponseEntity<ResponseRegistration> signUp(@RequestBody @Valid RegisterDto registerDto){

        if(!Objects.equals(registerDto.getPassword(), registerDto.getConfirmPassword())){
            return new ResponseEntity<>(new ResponseRegistration("password does not match"), HttpStatus.BAD_REQUEST);
        }
        if(!userRepository.existsByUsername(registerDto.getUsername())) {
            registrationService.register(registerDto);
            return ResponseEntity.ok(new ResponseRegistration("Registration successful"));
        }
        else
            return new ResponseEntity<>(new ResponseRegistration("username already exists"), HttpStatus.BAD_REQUEST);
    }
}
