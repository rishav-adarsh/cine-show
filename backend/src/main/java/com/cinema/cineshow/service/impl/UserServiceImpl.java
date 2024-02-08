package com.cinema.cineshow.service.impl;

import com.cinema.cineshow.model.Role;
import com.cinema.cineshow.model.User;
import com.cinema.cineshow.repo.RoleRepository;
import com.cinema.cineshow.repo.UserRepository;
import com.cinema.cineshow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User createUser(User user, Role role) throws Exception {
        User localUser = userRepository.findByUsername(user.getUsername());

        if(localUser != null) {
            System.out.println("User is already created!!");
            throw new Exception("User already exists!!");
        }else {
            roleRepository.save(role);
            user.setRole(role);
            localUser = this.userRepository.save(user);
        }

        return localUser;
    }

    @Override
    public User getUser(String username) {
        System.out.println("Fetcing user "+username);
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User updateUser(User user) {
        User localUser = this.userRepository.findByUsername(user.getUsername());

        if(localUser != null) {
            user.setId(localUser.getId());
        }

        return this.userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        System.out.println("Deleting user with uid: "+userId);
        this.userRepository.deleteById(userId);
    }
}
