package com.cinema.cineshow.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class HealthController {
    private static final String LOG_IDENTIFIER = "[HealthController]";

    @GetMapping("/health")
    public Map<String, String> health() {
        log.info("{}: Health check endpoint is called !!", LOG_IDENTIFIER);
        return Map.of("status", "UP", "message", "CineShow API is running");
    }
}
