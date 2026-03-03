package com.nisal.iceflame.service;

import com.nisal.iceflame.dto.AuthReq.RegisterRequest;
import com.nisal.iceflame.dto.AuthReq.SignInRequest;
import com.nisal.iceflame.dto.UserDto;
import com.nisal.iceflame.enums.Role;
import com.nisal.iceflame.exceptions.UserException;
import com.nisal.iceflame.mapper.AuthMapper;
import com.nisal.iceflame.mapper.UserMapper;
import com.nisal.iceflame.model.User;
import com.nisal.iceflame.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterRequest regUser(RegisterRequest dto) throws UserException {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserException("Email already in use", HttpStatus.CONFLICT);
        }

        User user = AuthMapper.toRegEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setIsActive(true);
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        return AuthMapper.toRegDto(savedUser);
    }

    public UserDto signIn(SignInRequest dto) throws UserException {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserException("User not found", HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UserException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }

        if (!user.getIsActive()) {
            throw new UserException("User is inactive!", HttpStatus.FORBIDDEN);
        }

        return UserMapper.toDto(user);
    }

    public RegisterRequest regAdmin(RegisterRequest dto) throws UserException {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserException("Email already in use", HttpStatus.CONFLICT);
        }

        User user = AuthMapper.toRegEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setIsActive(true);
        user.setRole(Role.ADMIN);

        User savedUser = userRepository.save(user);

        return AuthMapper.toRegDto(savedUser);
    }

    public UserDto signInAdmin(SignInRequest dto) throws UserException {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserException("User not found", HttpStatus.NOT_FOUND));

        if (user.getRole() != Role.ADMIN){
            throw new UserException("You don't have Access", HttpStatus.FORBIDDEN);
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UserException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }

        if (!user.getIsActive()) {
            throw new UserException("User is inactive!", HttpStatus.FORBIDDEN);
        }

        return UserMapper.toDto(user);
    }

}
