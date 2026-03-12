package com.ahmete.socialmedia.auth.auth.dto.request;

public class LogoutRequest {
	
	private Long userId;
	
	public LogoutRequest() {
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}