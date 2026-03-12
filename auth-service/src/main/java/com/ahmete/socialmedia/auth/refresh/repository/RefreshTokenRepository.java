package com.ahmete.socialmedia.auth.refresh.repository;

import com.ahmete.socialmedia.auth.refresh.entity.RefreshToken;
import com.ahmete.socialmedia.auth.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	
	Optional<RefreshToken> findByToken(String token);
	
	Optional<RefreshToken> findByUser(User user);
	
	void deleteByUser(User user);
}