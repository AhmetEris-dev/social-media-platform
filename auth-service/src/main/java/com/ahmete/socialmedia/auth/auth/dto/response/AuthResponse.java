package com.ahmete.socialmedia.auth.auth.dto.response;

public class AuthResponse {
	
	private String accessToken;
	private String refreshToken;
	private String tokenType;
	private Long userId;
	private String username;
	private String email;
	private String role;
	
	public AuthResponse() {
	}
	
	public AuthResponse(String accessToken,
	                    String refreshToken,
	                    String tokenType,
	                    Long userId,
	                    String username,
	                    String email,
	                    String role) {
		
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.tokenType = tokenType;
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.role = role;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}
	
	public String getTokenType() {
		return tokenType;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getRole() {
		return role;
	}
}