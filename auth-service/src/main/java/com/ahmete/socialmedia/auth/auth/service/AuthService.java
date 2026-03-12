package com.ahmete.socialmedia.auth.auth.service;

import com.ahmete.socialmedia.auth.auth.dto.request.LoginRequest;
import com.ahmete.socialmedia.auth.auth.dto.request.RegisterRequest;
import com.ahmete.socialmedia.auth.auth.dto.response.AuthResponse;
import com.ahmete.socialmedia.auth.common.exception.UserAlreadyExistsException;
import com.ahmete.socialmedia.auth.refresh.service.RefreshTokenService;
import com.ahmete.socialmedia.auth.security.service.JwtService;
import com.ahmete.socialmedia.auth.user.entity.Role;
import com.ahmete.socialmedia.auth.user.entity.User;
import com.ahmete.socialmedia.auth.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final RefreshTokenService refreshTokenService;
	
	public AuthService(UserRepository userRepository,
	                   PasswordEncoder passwordEncoder,
	                   JwtService jwtService,
	                   AuthenticationManager authenticationManager,
	                   RefreshTokenService refreshTokenService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.refreshTokenService = refreshTokenService;
	}
	
	public AuthResponse register(RegisterRequest request) {
		String username = request.getUsername().trim();
		String email = request.getEmail().trim().toLowerCase();
		
		if (userRepository.existsByUsername(username)) {
			throw new UserAlreadyExistsException("Bu username zaten kullanılıyor");
		}
		
		if (userRepository.existsByEmail(email)) {
			throw new UserAlreadyExistsException("Bu email zaten kullanılıyor");
		}
		
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(Role.USER);
		user.setEnabled(true);
		
		User savedUser = userRepository.save(user);
		String accessToken = jwtService.generateToken(user);
		
		var refreshToken = refreshTokenService.createRefreshToken(user);
		
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
	
	@Transactional(readOnly = true)
	public AuthResponse login(LoginRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(),
						request.getPassword()
				)
		);
		
		User user = userRepository.findByUsername(request.getUsername())
		                          .orElseThrow(() -> new BadCredentialsException("Geçersiz kullanıcı adı veya şifre"));
		
		String accessToken = jwtService.generateToken(user);
		
		var refreshToken = refreshTokenService.createRefreshToken(user);
		
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
}