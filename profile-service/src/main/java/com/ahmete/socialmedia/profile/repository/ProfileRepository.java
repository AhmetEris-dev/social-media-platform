package com.ahmete.socialmedia.profile.repository;

import com.ahmete.socialmedia.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
	
	Optional<Profile> findByUserId(Long userId);
	
	Optional<Profile> findByUsername(String username);
	
	boolean existsByUserId(Long userId);
	
	boolean existsByUsername(String username);
}