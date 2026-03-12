package com.ahmete.socialmedia.auth.user.repository;

import com.ahmete.socialmedia.auth.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	
	boolean existsByUsername(String username);
	
	boolean existsByEmail(String email);
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findByEmail(String email);
}