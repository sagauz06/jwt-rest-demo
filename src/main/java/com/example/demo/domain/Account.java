package com.example.demo.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "accounts")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
	private Long id;

	private String content;

	@OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
	private Person person;

//	@ManyToOne(fetch = FetchType.EAGER, optional = false)
//	@JoinColumn(name = "person_id", nullable = false)
//	@OnDelete(action = OnDeleteAction.CASCADE)
//	@JsonIgnore
//	private Character character;

	public Account() {

	}

	public Account(String content) {
		this.content = content;
	}

	public Account(Long id, String content) {
		super();
		this.id = id;
		this.content = content;
	}

	public Account(Long id, String content, Person person) {
		super();
		this.id = id;
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

	public Long getId() {
		return id;
	}

	public Long setId(Long id) {
		return id;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", content=" + content + ", person=" + person + "]";
	}

}
