package com.ahmete.socialmedia.auth.user.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
		name = "users",
		uniqueConstraints = {
				@UniqueConstraint(name = "uk_users_username", columnNames = "username"),
				@UniqueConstraint(name = "uk_users_email", columnNames = "email")
		}
)
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String username;
	
	@Column(nullable = false, length = 120)
	private String email;
	
	@Column(nullable = false, length = 255)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Role role;
	
	@Column(nullable = false)
	private Boolean enabled;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
	public User() {
	}
	
	public User(Long id,
	            String username,
	            String email,
	            String password,
	            Role role,
	            Boolean enabled,
	            LocalDateTime createdAt,
	            LocalDateTime updatedAt) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	@PrePersist
	public void prePersist() {
		LocalDateTime now = LocalDateTime.now();
		this.createdAt = now;
		this.updatedAt = now;
		
		if (this.enabled == null) {
			this.enabled = true;
		}
		
		if (this.role == null) {
			this.role = Role.USER;
		}
	}
	
	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public Boolean getEnabled() {
		return enabled;
	}
	
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}