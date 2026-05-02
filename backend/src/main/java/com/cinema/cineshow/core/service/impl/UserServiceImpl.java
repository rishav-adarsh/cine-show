package com.cinema.cineshow.core.service.impl;

import com.cinema.cineshow.infrastructure.entity.Role;
import com.cinema.cineshow.infrastructure.entity.User;
import com.cinema.cineshow.infrastructure.repository.RoleRepository;
import com.cinema.cineshow.infrastructure.repository.UserRepository;
import com.cinema.cineshow.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User createUser(User user) throws Exception {
        User localUser = userRepository.findByUsername(user.getUsername());

        if (localUser != null) {
            log.warn("User with username {} already exists", user.getUsername());
            throw new Exception("User already exists!!");
        } else {
            localUser = this.userRepository.save(user);
        }

        return localUser;
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user by username: {}", username);
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User getUserById(String userId) {
        log.info("Fetching user by id: {}", userId);
        return this.userRepository.findById(userId);
    }

    @Override
    public User updateUser(User user) {
        log.info("Updating user with id: {}", user.getCsid());
        return this.userRepository.save(user);
    }

    @Override
    public void deleteUser(String userId) {
        log.info("Deleting user with id: {}", userId);
        this.userRepository.deleteById(userId);
    }
}
