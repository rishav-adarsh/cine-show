package com.cinema.cineshow.api.mapper;

import com.cinema.cineshow.api.dto.UserDtos;
import com.cinema.cineshow.infrastructure.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserDtos.UserCreateRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setProfile(request.getProfileImageUrl());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setRoleId(request.getRoleId());
        user.setEnabled(true); // constant
        user.setIsDeleted(false);
        // csid is ignored
        return user;
    }

    public UserDtos.UserResponse toResponse(User user) {
        UserDtos.UserResponse response = new UserDtos.UserResponse();
        response.setCsid(user.getCsid());
        response.setUsername(user.getUsername());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setProfileImageUrl(user.getProfile());
        response.setEnabled(user.isEnabled());
        response.setRoleId(user.getRoleId());
        return response;
    }
}
