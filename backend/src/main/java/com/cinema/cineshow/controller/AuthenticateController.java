package com.cinema.cineshow.controller;

import com.cinema.cineshow.config.JwtUtils;
import com.cinema.cineshow.model.JwtRequest;
import com.cinema.cineshow.model.JwtResponse;
import com.cinema.cineshow.model.User;
import com.cinema.cineshow.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*")
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println("Getting req at /generate-token: "+jwtRequest.getUsername());
        try {
            this.authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        }catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw new Exception("User not found!!");
        }

        // After successful authentication:
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String jwtToken = this.jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(jwtToken));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (DisabledException e) {
            throw new Exception("User Disabled: "+e.getMessage());
        }catch (BadCredentialsException e) {
            throw new Exception("Invalid Credentials: "+e.getMessage());
        }
    }

    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal) {
        return (User)this.userDetailsService.loadUserByUsername(principal.getName());
    }

}
