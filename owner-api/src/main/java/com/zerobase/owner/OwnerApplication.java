package com.zerobase.owner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OwnerApplication {
	public static void main(String[] args) {
		SpringApplication.run(OwnerApplication.class, args);
	}
}
