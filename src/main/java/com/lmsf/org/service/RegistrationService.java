package com.lmsf.org.service;

import com.lmsf.org.dto.RegisterDto;
import com.lmsf.org.entity.Role;
import com.lmsf.org.entity.UserInfo;
import com.lmsf.org.exception.ConstraintsViolationException;
import com.lmsf.org.exception.PasswordMatchingException;
import com.lmsf.org.exception.RoleNotFoundException;
import com.lmsf.org.repository.RoleRepository;
import com.lmsf.org.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterDto registerDto){

        if(!Objects.equals(registerDto.getPassword(), registerDto.getConfirmPassword()))
            throw new PasswordMatchingException("password does not match");

        if(userRepository.existsByUsername(registerDto.getUsername()))
            throw new ConstraintsViolationException("username already exists");

        if(userRepository.existsByEmail(registerDto.getEmail()))
            throw new ConstraintsViolationException("email already exists");

        UserInfo user = new UserInfo();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());

        Role role = roleRepository.findByName("USER").orElseThrow(() -> new RoleNotFoundException("No such authority available"));
        user.setRoles(Collections.singletonList(role));

        userRepository.save(user);
    }

}
