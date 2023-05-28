package com.example.cafe;

import com.example.cafe.model.CafeUser;
import com.example.cafe.model.UserRole;
import com.example.cafe.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

	/**
	 * Provides persistence layer initializer
	 * @param repo user repository
	 */
	@Bean
	public CommandLineRunner buildDataLoader(UserRepository repo) {
		return args -> {
			repo.deleteAll();
			var passwordHash = new BCryptPasswordEncoder().encode("4444");
			for (int roleIndex = 0; roleIndex < 3; roleIndex++) {
				var role = UserRole.ALL[roleIndex];
				var email = role + "@cafe.com";
				var username = role + "User";
				var user = new CafeUser(null, email, username, role, passwordHash, null, null);
				repo.save(user);
			}
		};
	}
}
