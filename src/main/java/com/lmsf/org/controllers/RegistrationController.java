package com.lmsf.org.controllers;

import com.lmsf.org.dto.RegisterDto;
import com.lmsf.org.repository.UserRepository;
import com.lmsf.org.service.RegistrationService;
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
    public ResponseEntity<String> signUp(@RequestBody @Valid RegisterDto registerDto){

        if(!Objects.equals(registerDto.getPassword(), registerDto.getConfirmPassword())){
            return new ResponseEntity<>("password does not match", HttpStatus.BAD_REQUEST);
        }
        if(!userRepository.existsByUsername(registerDto.getUserName())) {
            registrationService.register(registerDto);
            return ResponseEntity.ok("Registration successful");
        }
        else
            return new ResponseEntity<>("username already exists", HttpStatus.BAD_REQUEST);
    }
}
