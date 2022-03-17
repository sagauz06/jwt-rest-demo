package com.example.demo.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "friends")
	private Set<Character> characters = new HashSet<>();

	private String friendName;

	public Friend() {

	}

	public Friend(String friendName) {
		this.friendName = friendName;
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

	@Override
	public String toString() {
		return "Friend [id=" + id + ", characters=" + characters + ", friendName=" + friendName + "]";
	}

}
