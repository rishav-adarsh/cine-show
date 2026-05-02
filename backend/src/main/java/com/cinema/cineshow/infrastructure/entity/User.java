package com.cinema.cineshow.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class User implements UserDetails {
    private String csid;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String profile;
    private boolean enabled = true;
    private String roleId;
    private boolean isDeleted = false;

    public User() {}

    public User(String csid, String username, String password, String firstName, String lastName, String email, String phone, String profile, boolean enabled, String roleId, boolean isDeleted) {
        this.csid = csid;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.profile = profile;
        this.enabled = enabled;
        this.roleId = roleId;
        this.isDeleted = isDeleted;
    }

    public String getCsid() { return csid; }
    public void setCsid(String csid) { this.csid = csid; }

    @Override
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    @Override
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getProfile() { return profile; }
    public void setProfile(String profile) { this.profile = profile; }

    @Override
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public String getRoleId() { return roleId; }
    public void setRoleId(String roleId) { this.roleId = roleId; }

    public boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> authoritySet = new HashSet<>();
        if (roleId != null) {
            authoritySet.add(new Authority(roleId));
        }
        return authoritySet;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() { return true; }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() { return true; }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() { return true; }
}
