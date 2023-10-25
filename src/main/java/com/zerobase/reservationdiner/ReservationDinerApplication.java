package com.zerobase.reservationdiner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ReservationDinerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationDinerApplication.class, args);
	}

}
