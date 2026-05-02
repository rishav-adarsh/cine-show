package com.cinema.cineshow.core.service;

import com.cinema.cineshow.infrastructure.entity.Role;
import com.cinema.cineshow.infrastructure.entity.User;

public interface UserService {
    User createUser(User user) throws Exception;

    User getUser(String username);

    User getUserById(String userId);

    User updateUser(User user);

    void deleteUser(String userId);
}
