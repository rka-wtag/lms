package com.lmsf.org.controllers;

import com.lmsf.org.dto.LoginDto;
import com.lmsf.org.service.CustomUserDetailsService;
import com.lmsf.org.service.JwtService;
import com.lmsf.org.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    public final JwtService jwtService;

    @PostMapping
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginDto loginDto){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()
            ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e){
            throw new UsernameNotFoundException("Bad Credentials");
        }
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDto.getUsername());
        final String jwt = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponseDto(jwt));
    }

}
