package com.example.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.demo.domain.Account;
import com.example.demo.domain.Person;
import com.example.demo.domain.repo.AccountRepository;
import com.example.demo.domain.repo.PersonRepository;
import com.example.demo.model.AccountModel;
import com.example.demo.model.AccountModelAssembler;

@RestController
@RequestMapping("/hateoas")
public class AccountController {
	@Autowired
	PersonRepository personRepository;

	private final AccountRepository repository;
	private final AccountModelAssembler assembler;

	AccountController(AccountRepository repository, AccountModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	@GetMapping("/accounts")
	public ResponseEntity<CollectionModel<AccountModel>> findAll() {
		return ResponseEntity.ok( //
				this.assembler.toCollectionModel(this.repository.findAll()));
	}

	@GetMapping("/accounts/{id}")
	public ResponseEntity<AccountModel> findOne(@PathVariable long id) {
		return this.repository.findById(id) //
				.map(this.assembler::toModel) //
				.map(ResponseEntity::ok) //
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/accounts")
	public ResponseEntity<?> newAccount(@RequestBody Account account) {
		Person person = new Person(account.getPerson().getName());
		personRepository.save(person);
		person.setAccount(account);
		account.setPerson(personRepository.findByName(account.getPerson().getName()));
		AccountModel accountModel = assembler.toModel(repository.save(account));
		personRepository.save(person);
//		repository.save(account);
		return ResponseEntity //
				.created(accountModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
				.body(accountModel);
	}

	@PutMapping("/accounts/{id}")
	public ResponseEntity<AccountModel> putAccount(@RequestBody Account account, @PathVariable Long id) {
		Account putAccount = repository.findById(id).map(tAccount -> {
			tAccount.setContent(account.getContent());
			return repository.save(tAccount);
		}).orElseGet(() -> {
			account.setId(id);
			return repository.save(account);
		});
		AccountModel accountModel = assembler.toModel(putAccount);
		return ResponseEntity //
				.created(accountModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
				.body(accountModel);
	}

//	@DeleteMapping("/accounts/{id}")
//	ResponseEntity<AccountModel> deleteEmployee(@PathVariable Long id) {
//		repository.deleteById(id);
//		personRepository.deleteById(id);

//		return ResponseEntity.noContent().build();
//	}
}
