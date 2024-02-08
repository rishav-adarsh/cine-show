package com.cinema.cineshow.repo;

import com.cinema.cineshow.model.Role;
import com.cinema.cineshow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
}
