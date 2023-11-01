package com.lmsf.org.service;

import com.lmsf.org.config.MyCustomUserDetails;
import com.lmsf.org.dto.RefreshTokenDto;
import com.lmsf.org.dto.RefreshTokenResponse;
import com.lmsf.org.dto.TokenResponseDto;
import com.lmsf.org.entity.RefreshToken;
import com.lmsf.org.entity.UserInfo;
import com.lmsf.org.exception.TokenRefreshException;
import com.lmsf.org.repository.RefreshTokenRepository;
import com.lmsf.org.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Transactional
    public String createRefreshToken(UserDetails userDetails){
        return jwtService.generateAccessToken(userDetails);
    }

    public void verifyRefreshToken(RefreshToken refreshToken){
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new TokenRefreshException("Refresh token was expired. Please make a new signing request");
        }
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token);
    }

    public MyCustomUserDetails createUserDetails(String username){
        UserInfo user = userRepository.findByUsername(username)
                .orElseThrow(() -> new TokenRefreshException("Invalid token"));

        Collection<GrantedAuthority> roles = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

        return new MyCustomUserDetails(user.getId(), user.getUsername(), user.getName(), user.getPassword(), roles);
    }

    public RefreshTokenResponse createToken(RefreshTokenDto refreshTokenDto){
        String username = "";
        try {
            username = jwtService.extractUsername(refreshTokenDto.getToken());
        } catch (Exception e){
            throw new TokenRefreshException(e.getMessage());
        }
        MyCustomUserDetails userDetails = createUserDetails(username);

        if(!jwtService.validateToken(refreshTokenDto.getToken(), userDetails)){
            throw new TokenRefreshException("Token is not valid");
        }
        String accessToken = jwtService.generateAccessToken(userDetails);
        String newRefreshToken = jwtService.generateRefreshToken(userDetails);

        return RefreshTokenResponse
                        .builder()
                        .accessToken(accessToken)
                        .refreshToken(newRefreshToken)
                        .build();
    }
}
