package com.ahmete.socialmedia.auth.security.filter;

import com.ahmete.socialmedia.auth.security.service.CustomUserDetailsService;
import com.ahmete.socialmedia.auth.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private final JwtService jwtService;
	private final CustomUserDetailsService customUserDetailsService;
	
	public JwtAuthenticationFilter(JwtService jwtService,
	                               CustomUserDetailsService customUserDetailsService) {
		this.jwtService = jwtService;
		this.customUserDetailsService = customUserDetailsService;
	}
	
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
	                                @NonNull HttpServletResponse response,
	                                @NonNull FilterChain filterChain) throws ServletException, IOException {
		
		final String authHeader = request.getHeader("Authorization");
		final String token;
		final String username;
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		token = authHeader.substring(7);
		
		try {
			username = jwtService.extractUsername(token);
		} catch (Exception e) {
			filterChain.doFilter(request, response);
			return;
		}
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
			
			if (jwtService.isTokenValid(token, userDetails.getUsername())) {
				UsernamePasswordAuthenticationToken authenticationToken =
						new UsernamePasswordAuthenticationToken(
								userDetails,
								null,
								userDetails.getAuthorities()
						);
				
				authenticationToken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request)
				);
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		
		filterChain.doFilter(request, response);
	}
}