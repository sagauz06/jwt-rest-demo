package com.example.demo.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Character;

public interface CharacterRepository extends JpaRepository<Character, Long> {

}
