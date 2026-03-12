package com.ahmete.socialmedia.profile.service;

import com.ahmete.socialmedia.profile.dto.request.CreateProfileRequest;
import com.ahmete.socialmedia.profile.dto.request.UpdateProfileRequest;
import com.ahmete.socialmedia.profile.dto.response.ProfileResponse;
import com.ahmete.socialmedia.profile.entity.Profile;
import com.ahmete.socialmedia.profile.exception.BusinessException;
import com.ahmete.socialmedia.profile.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {
	
	private final ProfileRepository profileRepository;
	
	public ProfileService(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}
	
	@Transactional
	public ProfileResponse createProfile(CreateProfileRequest request) {
		String normalizedUsername = normalizeUsername(request.getUsername());
		
		if (profileRepository.existsByUserId(request.getUserId())) {
			throw new BusinessException("Bu userId için profil zaten mevcut");
		}
		
		if (profileRepository.existsByUsername(normalizedUsername)) {
			throw new BusinessException("Bu username zaten kullanılıyor");
		}
		
		Profile profile = new Profile();
		profile.setUserId(request.getUserId());
		profile.setDisplayName(request.getDisplayName().trim());
		profile.setUsername(normalizedUsername);
		profile.setBio(request.getBio() != null ? request.getBio().trim() : null);
		profile.setAvatarUrl(request.getAvatarUrl() != null ? request.getAvatarUrl().trim() : null);
		
		Profile savedProfile = profileRepository.save(profile);
		return mapToResponse(savedProfile);
	}
	
	@Transactional(readOnly = true)
	public ProfileResponse getProfileByUserId(Long userId) {
		Profile profile = profileRepository.findByUserId(userId)
		                                   .orElseThrow(() -> new BusinessException("Profile bulunamadı. userId: " + userId));
		
		return mapToResponse(profile);
	}
	
	@Transactional(readOnly = true)
	public ProfileResponse getProfileByUsername(String username) {
		String normalizedUsername = normalizeUsername(username);
		
		Profile profile = profileRepository.findByUsername(normalizedUsername)
		                                   .orElseThrow(() -> new BusinessException("Profile bulunamadı. username: " + normalizedUsername));
		
		return mapToResponse(profile);
	}
	
	@Transactional
	public ProfileResponse updateProfile(Long userId, UpdateProfileRequest request) {
		String normalizedUsername = normalizeUsername(request.getUsername());
		
		Profile profile = profileRepository.findByUserId(userId)
		                                   .orElseThrow(() -> new BusinessException("Güncellenecek profile bulunamadı. userId: " + userId));
		
		profileRepository.findByUsername(normalizedUsername)
		                 .ifPresent(existingProfile -> {
			                 if (!existingProfile.getId().equals(profile.getId())) {
				                 throw new BusinessException("Bu username zaten kullanılıyor");
			                 }
		                 });
		
		profile.setDisplayName(request.getDisplayName().trim());
		profile.setUsername(normalizedUsername);
		profile.setBio(request.getBio() != null ? request.getBio().trim() : null);
		profile.setAvatarUrl(request.getAvatarUrl() != null ? request.getAvatarUrl().trim() : null);
		
		Profile updatedProfile = profileRepository.save(profile);
		return mapToResponse(updatedProfile);
	}
	
	private String normalizeUsername(String username) {
		return username.trim().toLowerCase();
	}
	
	private ProfileResponse mapToResponse(Profile profile) {
		return new ProfileResponse(
				profile.getId(),
				profile.getUserId(),
				profile.getDisplayName(),
				profile.getUsername(),
				profile.getBio(),
				profile.getAvatarUrl(),
				profile.getCreatedAt(),
				profile.getUpdatedAt()
		);
	}
}