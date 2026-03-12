package com.ahmete.socialmedia.profile.dto.response;

import java.time.LocalDateTime;

public class ProfileResponse {
	
	private Long id;
	private Long userId;
	private String displayName;
	private String username;
	private String bio;
	private String avatarUrl;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public ProfileResponse() {
	}
	
	public ProfileResponse(Long id,
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