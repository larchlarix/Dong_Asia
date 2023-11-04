package com.example.DongAisa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@EntityScan(basePackages = {"com.example.DongAisa"})
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class })
@SpringBootApplication(exclude={SecurityAutoConfiguration.class })
public class DongAisaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DongAisaApplication.class, args);
	}

}
