package com.example.demo;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.security.domain.ERole;
import com.example.demo.security.domain.Role;
import com.example.demo.security.domain.User;
import com.example.demo.security.domain.repo.RoleRepository;
import com.example.demo.security.domain.repo.UserRepository;

@Configuration
class LoadDatabase {
	@Autowired
	PasswordEncoder encoder;

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(RoleRepository roleRepository, UserRepository userRepository) {

		return args -> {

			log.info("Preloading " + roleRepository.save(new Role(ERole.ROLE_ADMIN)));
			log.info("Preloading " + roleRepository.save(new Role(ERole.ROLE_MODERATOR)));
			log.info("Preloading " + roleRepository.save(new Role(ERole.ROLE_USER)));

			Set<Role> roles = new HashSet<>();
			Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow();
			roles.add(userRole);
			log.info("Preloading "
					+ userRepository.save(new User("harvey", "email@mail.com", encoder.encode("123"), roles)));

			roleRepository.findAll().forEach(role -> {
				log.info("Preloaded " + role);
			});
			userRepository.findAll().forEach(user -> {
				log.info("Preloaded " + user);
			});
		};
	}
}
