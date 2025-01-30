package com.zerobase.owner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = { "com.zerobase.owner.repository", "com.zerobase.domain.repository.common" })
@EntityScan(basePackages = {"com.zerobase.owner.entity", "com.zerobase.domain.entity" })
public class OwnerApplication {
	public static void main(String[] args) {
		SpringApplication.run(OwnerApplication.class, args);
	}
}
