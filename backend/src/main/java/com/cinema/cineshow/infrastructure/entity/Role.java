package com.cinema.cineshow.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

public class Role {
    private String csid;
    private String roleName;

    public Role() {}

    public Role(String csid, String roleName) {
        this.csid = csid;
        this.roleName = roleName;
    }

    public String getCsid() { return csid; }
    public void setCsid(String csid) { this.csid = csid; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    public static RoleBuilder builder() {
        return new RoleBuilder();
    }

    public static class RoleBuilder {
        private String csid;
        private String roleName;

        RoleBuilder() {}

        public RoleBuilder csid(String csid) { this.csid = csid; return this; }
        public RoleBuilder roleName(String roleName) { this.roleName = roleName; return this; }

        public Role build() {
            return new Role(csid, roleName);
        }
    }
}
