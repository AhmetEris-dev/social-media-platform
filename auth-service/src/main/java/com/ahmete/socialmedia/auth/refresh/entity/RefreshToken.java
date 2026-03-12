package com.ahmete.socialmedia.auth.refresh.entity;

import com.ahmete.socialmedia.auth.user.entity.User;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 500)
	private String token;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(nullable = false)
	private Instant expiryDate;
	
	public RefreshToken() {
	}
	
	public Long getId() {
		return id;
	}
	
	public String getToken() {
		return token;
	}
	
	public User getUser() {
		return user;
	}
	
	public Instant getExpiryDate() {
		return expiryDate;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setExpiryDate(Instant expiryDate) {
		this.expiryDate = expiryDate;
	}
}