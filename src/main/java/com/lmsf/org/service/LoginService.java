package com.lmsf.org.service;

import com.lmsf.org.dto.LoginDto;
import com.lmsf.org.dto.RefreshTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public RefreshTokenResponse authenticate(LoginDto loginDto){
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

        final String jwt = jwtService.generateAccessToken(userDetails);
        final String refreshToken = refreshTokenService.createRefreshToken(userDetails);

        return RefreshTokenResponse
                        .builder()
                        .accessToken(jwt)
                        .refreshToken(refreshToken)
                        .build();
    }
}
