package com.cinema.cineshow.api.dto;

public class AuthDtos {
    private AuthDtos() {
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public LoginRequest() {}

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public static LoginRequestBuilder builder() {
            return new LoginRequestBuilder();
        }

        public static class LoginRequestBuilder {
            private String username;
            private String password;

            public LoginRequestBuilder username(String username) { this.username = username; return this; }
            public LoginRequestBuilder password(String password) { this.password = password; return this; }
            public LoginRequest build() { return new LoginRequest(username, password); }
        }
    }

    public static class TokenResponse {
        private String token;

        public TokenResponse() {}

        public TokenResponse(String token) {
            this.token = token;
        }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }

        public static TokenResponseBuilder builder() {
            return new TokenResponseBuilder();
        }

        public static class TokenResponseBuilder {
            private String token;

            public TokenResponseBuilder token(String token) { this.token = token; return this; }
            public TokenResponse build() { return new TokenResponse(token); }
        }
    }
}
