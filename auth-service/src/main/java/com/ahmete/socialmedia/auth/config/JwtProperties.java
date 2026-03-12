package com.ahmete.socialmedia.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
	
	private String secret;
	private long accessTokenExpiration;
	
	public JwtProperties() {
	}
	
	public String getSecret() {
		return secret;
	}
	
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	public long getAccessTokenExpiration() {
		return accessTokenExpiration;
	}
	
	public void setAccessTokenExpiration(long accessTokenExpiration) {
		this.accessTokenExpiration = accessTokenExpiration;
	}
}