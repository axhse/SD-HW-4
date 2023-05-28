package com.example.cafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Application entry point
 */
@SpringBootApplication(exclude = {
		ManagementWebSecurityAutoConfiguration.class,
		SecurityAutoConfiguration.class,
		UserDetailsServiceAutoConfiguration.class
})
@EnableJpaRepositories("com.example.cafe.repository")
public class Application {

	/**
	 * Starts the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
