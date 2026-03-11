package com.nisal.iceflame.service;

import com.nisal.iceflame.dto.UserDto;
import com.nisal.iceflame.exceptions.UserException;
import com.nisal.iceflame.mapper.UserMapper;
import com.nisal.iceflame.model.User;
import com.nisal.iceflame.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // GET USER
    public UserDto getUser(Long id){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found", HttpStatus.NOT_FOUND));

        return UserMapper.toDto(user);
    }

    // UPDATE USER
    public UserDto updateUser(Long id, UserDto dto){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found", HttpStatus.NOT_FOUND));

        user.setFullName(dto.getFullName());
        user.setProfileImageUrl(dto.getProfileImageUrl());
        user.setMobileNumber(dto.getMobileNumber());

        User updatedUser = userRepository.save(user);

        return UserMapper.toDto(updatedUser);
    }

    // DELETE USER
    public void deleteUser(Long id){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found", HttpStatus.NOT_FOUND));

        userRepository.delete(user);
    }
}