package com.ahmete.socialmedia.auth.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
	
	@NotBlank(message = "Username boş olamaz")
	@Size(min = 3, max = 50, message = "Username 3 ile 50 karakter arasında olmalıdır")
	private String username;
	
	@NotBlank(message = "Email boş olamaz")
	@Email(message = "Geçerli bir email adresi girilmelidir")
	@Size(max = 120, message = "Email en fazla 120 karakter olabilir")
	private String email;
	
	@NotBlank(message = "Password boş olamaz")
	@Size(min = 6, max = 100, message = "Password 6 ile 100 karakter arasında olmalıdır")
	private String password;
	
	public RegisterRequest() {
	}
	
	public RegisterRequest(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
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
}