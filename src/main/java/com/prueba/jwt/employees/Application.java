package com.prueba.jwt.employees;

import com.prueba.jwt.employees.model.User;
import com.prueba.jwt.employees.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository repo, PasswordEncoder encoder) {
		return args -> {

			log.info("Inicializando usuario administrador...");

			if (repo.findByUsername("admin").isEmpty()) {

				User user = new User();
				user.setUsername("admin");
				user.setPassword(encoder.encode("admin123*"));

				repo.save(user);

				log.info("Usuario admin creado correctamente");
			} else {
				log.info("El usuario admin ya existe");
			}
		};
	}
}