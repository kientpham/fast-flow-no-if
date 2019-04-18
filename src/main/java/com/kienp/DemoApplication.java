package com.kienp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableConfigurationProperties
@Configuration
public class DemoApplication {

	
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}