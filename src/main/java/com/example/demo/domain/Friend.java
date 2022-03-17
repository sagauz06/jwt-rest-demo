package com.example.demo.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "friends")
public class Friend {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friend_generator")
	private Long id;

	@ManyToMany
	@JoinTable(name = "characters_friends", joinColumns = @JoinColumn(name = "character_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
	Set<Character> characters;

	private String friendName;

	public Friend() {

	}

	public Set<Character> getCharacters() {
		return characters;
	}

	public void setCharacters(Set<Character> characters) {
		this.characters = characters;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public Long getId() {
		return id;
	}

}
