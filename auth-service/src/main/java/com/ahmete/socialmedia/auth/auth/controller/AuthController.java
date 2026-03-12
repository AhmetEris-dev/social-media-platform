package com.ahmete.socialmedia.auth.auth.controller;

import com.ahmete.socialmedia.auth.auth.dto.request.LoginRequest;
import com.ahmete.socialmedia.auth.auth.dto.request.LogoutRequest;
import com.ahmete.socialmedia.auth.auth.dto.request.RefreshRequest;
import com.ahmete.socialmedia.auth.auth.dto.request.RegisterRequest;
import com.ahmete.socialmedia.auth.auth.dto.response.AuthResponse;
import com.ahmete.socialmedia.auth.auth.service.AuthService;
import com.ahmete.socialmedia.auth.refresh.repository.RefreshTokenRepository;
import com.ahmete.socialmedia.auth.refresh.service.RefreshTokenService;
import com.ahmete.socialmedia.auth.security.service.JwtService;
import com.ahmete.socialmedia.auth.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	private final AuthService authService;
	private final RefreshTokenService refreshTokenService;
	private final RefreshTokenRepository refreshTokenRepository;
	private final JwtService jwtService;
	private final UserRepository userRepository;
	
	public AuthController(AuthService authService,
	                      RefreshTokenService refreshTokenService,
	                      RefreshTokenRepository refreshTokenRepository,
	                      JwtService jwtService,
	                      UserRepository userRepository) {
		
		this.authService = authService;
		this.refreshTokenService = refreshTokenService;
		this.refreshTokenRepository = refreshTokenRepository;
		this.jwtService = jwtService;
		this.userRepository = userRepository;
	}
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
		return authService.register(request);
	}
	
	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public AuthResponse login(@Valid @RequestBody LoginRequest request) {
		return authService.login(request);
	}
	
	@PostMapping("/refresh")
	public AuthResponse refresh(@RequestBody RefreshRequest request) {
		
		var refreshToken = refreshTokenRepository
				.findByToken(request.getRefreshToken())
				.orElseThrow(() -> new RuntimeException("Refresh token not found"));
		
		refreshTokenService.verifyExpiration(refreshToken);
		
		var user = refreshToken.getUser();
		
		String accessToken = jwtService.generateToken(user);
		
		return new AuthResponse(
				accessToken,
				refreshToken.getToken(),
				"Bearer",
				user.getId(),
				user.getUsername(),
				user.getEmail(),
				user.getRole().name()
		);
	}
	
	@PostMapping("/logout")
	public String logout(@RequestBody LogoutRequest request) {
		
		var user = userRepository.findById(request.getUserId())
		                         .orElseThrow(() -> new RuntimeException("User not found"));
		
		refreshTokenService.deleteByUser(user);
		
		return "Logout successful";
	}
}