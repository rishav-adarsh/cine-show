package com.cinema.cineshow.api.dto;

public class TheatreDtos {
    private TheatreDtos() {
    }

    public static class TheatreUpsertRequest {
        private String theatreName;
        private String location;

        public String getTheatreName() {
            return theatreName;
        }

        public void setTheatreName(String theatreName) {
            this.theatreName = theatreName;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }

    public static class TheatreResponse {
        private String theatreId;
        private String theatreName;
        private String location;

        public String getTheatreId() {
            return theatreId;
        }

        public void setTheatreId(String theatreId) {
            this.theatreId = theatreId;
        }

        public String getTheatreName() {
            return theatreName;
        }

        public void setTheatreName(String theatreName) {
            this.theatreName = theatreName;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
