package com.cinema.cineshow.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

public class Theatre {
    private String csid;

    private String location;
    private String theatreName;
    private boolean isDeleted = false;

    public Theatre() {}
    public Theatre(String csid, String location, String theatreName) {
        this.csid = csid;
        this.location = location;
        this.theatreName = theatreName;
    }

    public String getCsid() { return csid; }
    public void setCsid(String csid) { this.csid = csid; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getTheatreName() { return theatreName; }
    public void setTheatreName(String theatreName) { this.theatreName = theatreName; }

    public boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }

    public static TheatreBuilder builder() {
        return new TheatreBuilder();
    }

    public static class TheatreBuilder {
        private String csid;
        private String location;
        private String theatreName;
        private boolean isDeleted = false;

        TheatreBuilder() {}

        public TheatreBuilder csid(String csid) { this.csid = csid; return this; }
        public TheatreBuilder location(String location) { this.location = location; return this; }
        public TheatreBuilder theatreName(String theatreName) { this.theatreName = theatreName; return this; }
        public TheatreBuilder isDeleted(boolean isDeleted) { this.isDeleted = isDeleted; return this; }

        public Theatre build() {
            return new Theatre(csid, location, theatreName);
        }
    }
}
