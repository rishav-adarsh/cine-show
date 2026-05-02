package com.cinema.cineshow.api.controller;

import com.cinema.cineshow.api.dto.ApiResponse;
import com.cinema.cineshow.api.dto.AuthDtos;
import com.cinema.cineshow.api.dto.UserDtos;
import com.cinema.cineshow.api.mapper.UserMapper;
import com.cinema.cineshow.infrastructure.entity.User;
import com.cinema.cineshow.infrastructure.security.JwtUtils;
import com.cinema.cineshow.core.service.impl.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthenticateController {
    private static final Logger log = LoggerFactory.getLogger(AuthenticateController.class);

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;

    public AuthenticateController(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtils jwtUtils, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.userMapper = userMapper;
    }

    @PostMapping("/generate-token")
    public ResponseEntity<ApiResponse<AuthDtos.TokenResponse>> generateToken(@RequestBody AuthDtos.LoginRequest loginRequest) {
        log.info("Generating token for user: {}", loginRequest.getUsername());
        authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String jwtToken = this.jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(ApiResponse.success(new AuthDtos.TokenResponse(jwtToken), "Token generated successfully"));
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @GetMapping("/current-user")
    public ResponseEntity<ApiResponse<UserDtos.UserResponse>> getCurrentUser(Principal principal) {
        User user = (User) this.userDetailsService.loadUserByUsername(principal.getName());
        UserDtos.UserResponse response = userMapper.toResponse(user);
        return ResponseEntity.ok(ApiResponse.success(response, "Current user fetched successfully"));
    }
}
