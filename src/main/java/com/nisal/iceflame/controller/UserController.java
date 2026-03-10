package com.nisal.iceflame.controller;

import com.nisal.iceflame.dto.UserDto;
import com.nisal.iceflame.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // GET USER
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    // UPDATE USER
    @PutMapping("/{id}")
    public UserDto updateUser(
            @PathVariable Long id,
            @RequestBody UserDto dto
    ) {
        return userService.updateUser(id, dto);
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }

}