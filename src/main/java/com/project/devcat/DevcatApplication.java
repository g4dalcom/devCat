package com.project.devcat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DevcatApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevcatApplication.class, args);
	}

}
