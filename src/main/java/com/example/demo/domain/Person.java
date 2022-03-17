package com.example.demo.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "persons")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_generator")
	private Long id;

	@NotNull
	@Size(min = 1, message = "Name must be at least 1 characters long")
	private String name;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	private Account account;

	private Date createdAt;

	@PrePersist
	void createdAt() {
		this.createdAt = new Date();
	}

	public Person() {

	}

	public Person(@NotNull @Size(min = 1, message = "Name must be at least 1 characters long") String name,
			Account account, Date createdAt) {
		super();
		this.name = name;
		this.account = account;
		this.createdAt = createdAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", account=" + account + ", createdAt=" + createdAt + "]";
	}

}
