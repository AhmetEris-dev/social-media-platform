package com.ahmete.socialmedia.auth.security.service;

import com.ahmete.socialmedia.auth.config.JwtProperties;
import com.ahmete.socialmedia.auth.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
	
	private final JwtProperties jwtProperties;
	
	public JwtService(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
	}
	
	public String generateToken(User user) {
		Date now = new Date();
		Date expirationDate = new Date(now.getTime() + jwtProperties.getAccessTokenExpiration());
		
		return Jwts.builder()
		           .subject(user.getUsername())
		           .claim("userId", user.getId())
		           .claim("role", user.getRole().name())
		           .issuedAt(now)
		           .expiration(expirationDate)
		           .signWith(getSigningKey())
		           .compact();
	}
	
	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	public Long extractUserId(String token) {
		Object userId = extractAllClaims(token).get("userId");
		if (userId instanceof Integer integerValue) {
			return integerValue.longValue();
		}
		if (userId instanceof Long longValue) {
			return longValue;
		}
		return Long.parseLong(String.valueOf(userId));
	}
	
	public String extractRole(String token) {
		Object role = extractAllClaims(token).get("role");
		return String.valueOf(role);
	}
	
	public boolean isTokenValid(String token, String username) {
		String extractedUsername = extractUsername(token);
		return extractedUsername.equals(username) && !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
		Date expiration = extractAllClaims(token).getExpiration();
		return expiration.before(new Date());
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
		           .verifyWith(getSigningKey())
		           .build()
		           .parseSignedClaims(token)
		           .getPayload();
	}
	
	private SecretKey getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
		return Keys.hmacShaKeyFor(keyBytes);
	}
}