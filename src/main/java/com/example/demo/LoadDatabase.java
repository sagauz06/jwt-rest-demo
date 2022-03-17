package com.example.demo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.domain.Friend;
import com.example.demo.domain.Person;
import com.example.demo.domain.Account;
import com.example.demo.domain.Character;
import com.example.demo.domain.repo.AccountRepository;
import com.example.demo.domain.repo.CharacterRepository;
import com.example.demo.domain.repo.FriendRepository;
import com.example.demo.domain.repo.PersonRepository;
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
	CommandLineRunner initDatabase(RoleRepository roleRepository, UserRepository userRepository,
			AccountRepository accountRepository, CharacterRepository characterRepository,
			FriendRepository friendRepository, PersonRepository personRepository) {

		return args -> {
//			新增權限
			log.info("Preloading " + roleRepository.save(new Role(ERole.ROLE_ADMIN)));
			log.info("Preloading " + roleRepository.save(new Role(ERole.ROLE_MODERATOR)));
			log.info("Preloading " + roleRepository.save(new Role(ERole.ROLE_USER)));
//			新增使用者
			Set<Role> roles = new HashSet<>();
			Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow();
			roles.add(userRole);
			log.info("Preloading "
					+ userRepository.save(new User("harvey", "email@mail.com", encoder.encode("123"), roles)));
//			新增角色好友
			log.info("Preloading " + friendRepository.save(new Friend("小明")));
			log.info("Preloading " + friendRepository.save(new Friend("小王")));
			log.info("Preloading " + friendRepository.save(new Friend("小花")));
//			新增人物帳號
			log.info("Preloading " + accountRepository.save(new Account("學生")));
			log.info("Preloading " + accountRepository.save(new Account("上班族")));
			log.info("Preloading " + accountRepository.save(new Account("GM")));
//			新增人物
			Account account1 = accountRepository.findByContent("學生").orElseThrow();
			Account account2 = accountRepository.findByContent("上班族").orElseThrow();
			Account account3 = accountRepository.findByContent("GM").orElseThrow();
			log.info("Preloading " + personRepository.save(new Person("玩家A", account1)));
			log.info("Preloading " + personRepository.save(new Person("玩家B", account2)));
			log.info("Preloading " + personRepository.save(new Person("玩家C", account3)));
//			新增角色
			Set<Friend> friends1 = new HashSet<>();
			Set<Friend> friends2 = new HashSet<>();
			Set<Friend> friends3 = new HashSet<>();
			Friend friend1 = friendRepository.findByFriendName("小明").orElseThrow();
			Friend friend2 = friendRepository.findByFriendName("小王").orElseThrow();
			Friend friend3 = friendRepository.findByFriendName("小花").orElseThrow();
			friends1.add(friend1);
			friends1.add(friend2);
			friends1.add(friend3);
			friends2.add(friend3);
			log.info("Preloading " + characterRepository.save(new Character("弓箭手", friends1, account1)));
			log.info("Preloading " + characterRepository.save(new Character("法師", friends2, account2)));
			log.info("Preloading " + characterRepository.save(new Character("劍士", friends3, account3)));

//			列印
			roleRepository.findAll().forEach(role -> {
				log.info("Preloaded " + role);
			});
			userRepository.findAll().forEach(user -> {
				log.info("Preloaded " + user);
			});
		};
	}
}
