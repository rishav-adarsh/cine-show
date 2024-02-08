package com.cinema.cineshow;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CineShowApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CineShowApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Application starts running...");
	}
}
