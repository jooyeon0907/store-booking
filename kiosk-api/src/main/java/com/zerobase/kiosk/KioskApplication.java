package com.zerobase.kiosk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = { "com.zerobase.kiosk.repository", "com.zerobase.domain.repository.common" })
@EntityScan(basePackages = {"com.zerobase.domain.entity" })
public class KioskApplication {
	public static void main(String[] args) {
		SpringApplication.run(KioskApplication.class, args);
	}
}
