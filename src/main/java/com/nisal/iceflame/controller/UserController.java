package com.nisal.iceflame.controller;

import com.nisal.iceflame.dto.UserDto;
import com.nisal.iceflame.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // ✅ GET ALL USERS (ADMIN)
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    // ✅ UPDATE ROLE (ADMIN)
//    @PutMapping("/role/{id}")
//    public UserDto updateRole(
//            @PathVariable Long id,
//            @RequestParam String role
//    ) {
//        return userService.updateUserRole(id, role);
//    }

    // ✅ ACTIVATE / DEACTIVATE USER
    @PutMapping("/status/{id}")
    public UserDto updateStatus(
            @PathVariable Long id,
            @RequestParam Boolean active
    ) {
        return userService.updateUserStatus(id, active);
    }

}