package com.example.userservice;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class SocialMediaApplication {
	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(SocialMediaApplication.class, args);

	}

	@PostConstruct
	public void printServerPort() {
		String port = env.getProperty("server.port");
		System.out.println("Server is running on port: " + port);
	}

}
