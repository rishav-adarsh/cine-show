package com.cinema.cineshow.service;

import com.cinema.cineshow.model.Role;
import com.cinema.cineshow.model.User;

public interface UserService {
    public User createUser(User user, Role role) throws Exception;

    public User getUser(String username);

    public User updateUser(User user);

    public void deleteUser(Long userId);
}
