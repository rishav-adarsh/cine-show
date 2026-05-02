package com.cinema.cineshow.api.controller;

import com.cinema.cineshow.api.dto.ApiResponse;
import com.cinema.cineshow.api.dto.UserDtos;
import com.cinema.cineshow.infrastructure.entity.User;
import com.cinema.cineshow.api.mapper.UserMapper;
import com.cinema.cineshow.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDtos.UserResponse>> createUser(@RequestBody UserDtos.UserCreateRequest request) throws Exception {
        log.info("Creating user: {}", request.getUsername());
        
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRoleId() == null) {
            user.setRoleId("default-role-csid");
        }

        User createdUser = this.userService.createUser(user);
        UserDtos.UserResponse response = userMapper.toResponse(createdUser);
        return ResponseEntity.ok(ApiResponse.success(response, "User created successfully"));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDtos.UserResponse>> getUser(@PathVariable("userId") String userId) {
        log.info("Fetching user with id: {}", userId);
        User user = this.userService.getUserById(userId);
        UserDtos.UserResponse response = userMapper.toResponse(user);
        return ResponseEntity.ok(ApiResponse.success(response, "User fetched successfully"));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDtos.UserResponse>> updateUser(@PathVariable String userId, @RequestBody UserDtos.UserUpdateRequest request) {
        log.info("Updating user with id: {}", userId);
        User user = this.userService.getUserById(userId);
        if (user != null) {
            if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
            if (request.getLastName() != null) user.setLastName(request.getLastName());
            if (request.getEmail() != null) user.setEmail(request.getEmail());
            if (request.getPhone() != null) user.setPhone(request.getPhone());
            if (request.getProfileImageUrl() != null) user.setProfile(request.getProfileImageUrl());
            if (request.getEnabled() != null) user.setEnabled(request.getEnabled());
            
            User updatedUser = this.userService.updateUser(user);
            UserDtos.UserResponse response = userMapper.toResponse(updatedUser);
            return ResponseEntity.ok(ApiResponse.success(response, "User updated successfully"));
        }
        return ResponseEntity.status(404).body(ApiResponse.error(404, "NOT_FOUND", "User not found"));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable("userId") String userId) {
        log.info("Deleting user with id: {}", userId);
        this.userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.success(null, "User deleted successfully"));
    }
}
