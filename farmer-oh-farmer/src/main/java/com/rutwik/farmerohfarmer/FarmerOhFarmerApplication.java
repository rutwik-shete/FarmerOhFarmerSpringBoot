package com.rutwik.farmerohfarmer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FarmerOhFarmerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmerOhFarmerApplication.class, args);
	}

}
