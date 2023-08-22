package com.goaltracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GoaltrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoaltrackerApplication.class, args);
	}

}
