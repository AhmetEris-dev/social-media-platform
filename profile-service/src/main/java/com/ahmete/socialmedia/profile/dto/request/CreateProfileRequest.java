package com.ahmete.socialmedia.profile.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateProfileRequest {
	
	@NotNull(message = "userId boş olamaz")
	private Long userId;
	
	@NotBlank(message = "displayName boş olamaz")
	@Size(max = 100, message = "displayName en fazla 100 karakter olabilir")
	private String displayName;
	
	@NotBlank(message = "username boş olamaz")
	@Size(min = 3, max = 30, message = "username 3 ile 30 karakter arasında olmalıdır")
	@Pattern(
			regexp = "^[a-zA-Z0-9._]+$",
			message = "username sadece harf, rakam, nokta ve alt çizgi içerebilir"
	)
	private String username;
	
	@Size(max = 500, message = "bio en fazla 500 karakter olabilir")
	private String bio;
	
	@Size(max = 500, message = "avatarUrl en fazla 500 karakter olabilir")
	private String avatarUrl;
	
	public CreateProfileRequest() {
	}
	
	public CreateProfileRequest(Long userId,
	                            String displayName,
	                            String username,
	                            String bio,
	                            String avatarUrl) {
		this.userId = userId;
		this.displayName = displayName;
		this.username = username;
		this.bio = bio;
		this.avatarUrl = avatarUrl;
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
}