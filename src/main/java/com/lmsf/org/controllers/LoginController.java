package com.lmsf.org.controllers;

import com.lmsf.org.dto.LoginDto;
import com.lmsf.org.service.CustomUserDetailsService;
import com.lmsf.org.service.JwtService;
import com.lmsf.org.util.ResponseLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    public final JwtService jwtService;

    @PostMapping
    public ResponseEntity<ResponseLogin> login(@RequestBody @Valid LoginDto loginDto){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()
            ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e){
            return new ResponseEntity<>(new ResponseLogin("Bad Credentials"), HttpStatus.BAD_REQUEST);
        }
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDto.getUsername());

        final String jwt = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new ResponseLogin(jwt));
    }

}
