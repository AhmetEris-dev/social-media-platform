package com.ahmete.socialmedia.profile.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
		name = "profiles",
		uniqueConstraints = {
				@UniqueConstraint(name = "uk_profiles_user_id", columnNames = "user_id"),
				@UniqueConstraint(name = "uk_profiles_username", columnNames = "username")
		},
		indexes = {
				@Index(name = "idx_profiles_user_id", columnList = "user_id"),
				@Index(name = "idx_profiles_username", columnList = "username")
		}
)
public class Profile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_id", nullable = false, unique = true)
	private Long userId;
	
	@Column(name = "display_name", nullable = false, length = 100)
	private String displayName;
	
	@Column(name = "username", nullable = false, unique = true, length = 30)
	private String username;
	
	@Column(name = "bio", length = 500)
	private String bio;
	
	@Column(name = "avatar_url", length = 500)
	private String avatarUrl;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
	
	public Profile() {
	}
	
	public Profile(Long id,
	               Long userId,
	               String displayName,
	               String username,
	               String bio,
	               String avatarUrl,
	               LocalDateTime createdAt,
	               LocalDateTime updatedAt) {
		this.id = id;
		this.userId = userId;
		this.displayName = displayName;
		this.username = username;
		this.bio = bio;
		this.avatarUrl = avatarUrl;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	@PrePersist
	public void prePersist() {
		LocalDateTime now = LocalDateTime.now();
		this.createdAt = now;
		this.updatedAt = now;
	}
	
	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
	
	public Long getId() {
		return id;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getBio() {
		return bio;
	}
	
	public String getAvatarUrl() {
		return avatarUrl;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setBio(String bio) {
		this.bio = bio;
	}
	
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}