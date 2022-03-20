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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "persons")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_generator")
	private Long id;

	@NotNull
	@Size(min = 1, message = "Name must be at least 1 characters long")
	private String name;

//	https://stackoverflow.com/questions/23645091/spring-data-jpa-and-hibernate-detached-entity-passed-to-persist-on-manytomany-re
//	這個會造成資料初始化問題細節參考連結，但這個必要，loadDataBase已更改寫法，有關連的資料都先個存一次，再把有@JoinColumn的表查出需要的關聯pojo再set()然後再存一次，以下範例
//	Person person1 = new Person("玩家A"); //
//	personRepository.save(person1); //
//	person1.setAccount(accountRepository.findById(1L).orElseThrow()); //
//	log.info("Update " + personRepository.save(person1)); //
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id") // , referencedColumnName = "id"
//	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore // 這個單元測試需要
	private Account account;

	private Date createdAt;

	@PrePersist
	void createdAt() {
		this.createdAt = new Date();
	}

	public Person() {

	}

	public Person(@NotNull @Size(min = 1, message = "Name must be at least 1 characters long") String name) {
		super();
		this.name = name;
		this.createdAt = new Date();
	}

	public Person(Long id, @NotNull @Size(min = 1, message = "Name must be at least 1 characters long") String name,
			Date createdAt) {
		super();
		this.id = id;
		this.name = name;
		this.createdAt = new Date();
	}

	public Person(@NotNull @Size(min = 1, message = "Name must be at least 1 characters long") String name,
			Account account) {
		super();
		this.name = name;
		this.account = account;
	}

	public Person(Long id, @NotNull @Size(min = 1, message = "Name must be at least 1 characters long") String name) {
		super();
		this.id = id;
		this.name = name;
		this.createdAt = new Date();
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
