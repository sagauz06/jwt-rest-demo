package com.example.demo.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {

}
