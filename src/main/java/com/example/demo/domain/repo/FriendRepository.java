package com.example.demo.domain.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.domain.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {
	Optional<Friend> findByFriendName(String friendName);
}
