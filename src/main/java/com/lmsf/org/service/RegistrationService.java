package com.lmsf.org.service;

import com.lmsf.org.dto.RegisterDto;
import com.lmsf.org.entity.Role;
import com.lmsf.org.entity.UserInfo;
import com.lmsf.org.repository.RoleRepository;
import com.lmsf.org.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class RegistrationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterDto registerDto){
        UserInfo user = new UserInfo();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role role = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(role));

        userRepository.save(user);
    }

}
