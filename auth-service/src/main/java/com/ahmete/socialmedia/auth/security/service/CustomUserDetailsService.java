package com.ahmete.socialmedia.auth.security.service;

import com.ahmete.socialmedia.auth.user.entity.User;
import com.ahmete.socialmedia.auth.user.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
		                          .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + username));
		
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				user.getEnabled(),
				true,
				true,
				true,
				List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
		);
	}
}