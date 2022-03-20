package com.example.demo.model;

import org.springframework.hateoas.RepresentationModel;

import com.example.demo.domain.Account;
import com.example.demo.domain.Person;

public class AccountModel extends RepresentationModel<AccountModel> {
	private String content;
	private Person person;

	public AccountModel(Account account) {
		super();
		this.content = account.getContent();
		this.person = account.getPerson();
	}

	public AccountModel(String content, Person person) {
		super();
		this.content = content;
		this.person = person;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
