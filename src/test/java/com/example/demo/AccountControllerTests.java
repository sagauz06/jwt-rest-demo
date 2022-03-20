package com.example.demo;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.example.demo.controllers.AccountController;
import com.example.demo.domain.Account;
import com.example.demo.domain.Person;
import com.example.demo.domain.repo.AccountRepository;
import com.example.demo.domain.repo.PersonRepository;
import com.example.demo.model.AccountModelAssembler;
import com.example.demo.security.UserDetailsServiceImpl;
import com.example.demo.security.jwt.AuthEntryPointJwt;
import com.example.demo.security.jwt.JwtUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
@Import({ AccountModelAssembler.class, JwtUtils.class })
//@AutoConfigureMockMvc
public class AccountControllerTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AccountRepository accountRepository;
	@MockBean
	private PersonRepository personRepository;
//	https://stackoverflow.com/questions/70294518/no-qualifying-bean-of-type-org-springframework-security-core-userdetails-userde
	@MockBean
	UserDetailsServiceImpl userDetailsService;
	@MockBean
	AuthEntryPointJwt authEntryPointJwt;
//	@MockBean
//	JwtUtils jwtUtils;

	@BeforeEach
	public void setUp() {
//		Person person1 = new Person(1L, "playerA", new Date());
//		Person person2 = new Person(2L, "playerB", new Date());
//		Person person3 = new Person(3L, "playerC", new Date());
		Person person1 = new Person("playerA");
		Person person2 = new Person("playerB");
		Person person3 = new Person("playerC");
		personRepository.save(person1);
		personRepository.save(person2);
		personRepository.save(person3);

//		Account account1 = new Account(1L, "student");
//		Account account2 = new Account(2L, "worker");
//		Account account3 = new Account(3L, "GM");
		Account account1 = new Account("student");
		Account account2 = new Account("worker");
		Account account3 = new Account("GM");
		accountRepository.save(account1);
		accountRepository.save(account2);
		accountRepository.save(account3);

		person1.setAccount(accountRepository.getById(1L));
		person2.setAccount(accountRepository.getById(2L));
		person3.setAccount(accountRepository.getById(3L));
		personRepository.save(person1);
		personRepository.save(person2);
		personRepository.save(person3);
//		account1.setPerson(person1);
//		account2.setPerson(person2);
//		account3.setPerson(person3);

//		List<Account> accountList = new ArrayList<>();
//		accountList.add(account1);
//		accountList.add(account2);
//		accountList.add(account3);
//		given(accountRepository.findAll()).willReturn(accountList);
	}

	@Test
	public void getShouldFetchAHalDocument() throws Exception {
		Person person1 = new Person(1L, "playerA");
		Person person2 = new Person(2L, "playerB");
		given(accountRepository.findAll()).willReturn(Arrays.asList( //
				new Account(1L, "student", person1), //
				new Account(2L, "worker", person2)));

		mvc.perform(get("/hateoas/accounts").accept(MediaTypes.HAL_JSON_VALUE)) //
				.andDo(print()) //
				.andExpect(status().isOk()) //
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE)) //
				.andExpect(jsonPath("$._links.self.href", is("http://localhost/hateoas/accounts"))) //
				.andReturn();
//				.andDo(MockMvcResultHandlers.print());
	}

}
