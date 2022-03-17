package com.example.demo.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "characters")
public class Character {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "character_generator")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "account_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
//	@JsonIgnore
	private Account account;

	@ManyToMany
	@JoinTable(name = "characters_friends", joinColumns = @JoinColumn(name = "character_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
	private Set<Friend> friends;

	private String characterName;

	public Character() {

	}

	public Character(String characterName, Set<Friend> friends, Account account) {
		this.characterName = characterName;
		this.friends = friends;
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}

	public Long getId() {
		return id;
	}

	public Set<Friend> getFriends() {
		return friends;
	}

	public void setFriends(Set<Friend> friends) {
		this.friends = friends;
	}

	@Override
	public String toString() {
		return "Character [id=" + id + ", account=" + account + ", friends=" + friends + ", characterName="
				+ characterName + "]";
	}

}
