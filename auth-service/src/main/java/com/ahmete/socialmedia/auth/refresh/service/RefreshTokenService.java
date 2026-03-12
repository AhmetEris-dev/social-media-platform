package com.ahmete.socialmedia.auth.refresh.service;

import com.ahmete.socialmedia.auth.refresh.entity.RefreshToken;
import com.ahmete.socialmedia.auth.refresh.repository.RefreshTokenRepository;
import com.ahmete.socialmedia.auth.user.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
	
	private final RefreshTokenRepository refreshTokenRepository;
	
	@Value("${jwt.refresh-expiration}")
	private Long refreshTokenDurationMs;
	
	public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
	}
	
	public RefreshToken createRefreshToken(User user) {
		
		refreshTokenRepository.deleteByUser(user);
		
		RefreshToken refreshToken = new RefreshToken();
		
		refreshToken.setUser(user);
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
		
		return refreshTokenRepository.save(refreshToken);
	}
	
	public RefreshToken verifyExpiration(RefreshToken token) {
		
		if (token.getExpiryDate().isBefore(Instant.now())) {
			refreshTokenRepository.delete(token);
			throw new RuntimeException("Refresh token expired");
		}
		
		return token;
	}
	
	public void deleteByUser(User user) {
		refreshTokenRepository.deleteByUser(user);
	}
}