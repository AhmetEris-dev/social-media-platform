package com.ahmete.socialmedia.profile.controller;

import com.ahmete.socialmedia.profile.dto.request.CreateProfileRequest;
import com.ahmete.socialmedia.profile.dto.request.UpdateProfileRequest;
import com.ahmete.socialmedia.profile.dto.response.ProfileResponse;
import com.ahmete.socialmedia.profile.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {
	
	private final ProfileService profileService;
	
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProfileResponse createProfile(@Valid @RequestBody CreateProfileRequest request) {
		return profileService.createProfile(request);
	}
	
	@GetMapping("/user/{userId}")
	public ProfileResponse getProfileByUserId(@PathVariable Long userId) {
		return profileService.getProfileByUserId(userId);
	}
	
	@GetMapping("/username/{username}")
	public ProfileResponse getProfileByUsername(@PathVariable String username) {
		return profileService.getProfileByUsername(username);
	}
	
	@PutMapping("/user/{userId}")
	public ProfileResponse updateProfile(@PathVariable Long userId,
	                                     @Valid @RequestBody UpdateProfileRequest request) {
		return profileService.updateProfile(userId, request);
	}
}