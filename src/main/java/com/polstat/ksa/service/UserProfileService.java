package com.polstat.ksa.service;
import com.polstat.ksa.auth.exception.UserNotFoundException;
import com.polstat.ksa.auth.exception.UserProfileNotFoundException;
import com.polstat.ksa.dto.UserProfileDto;
import com.polstat.ksa.entity.Kabupaten;
import com.polstat.ksa.entity.User;
import com.polstat.ksa.entity.UserProfile;
import com.polstat.ksa.mapper.UserProfileMapper;
import com.polstat.ksa.repository.KabupatenRepository;
import com.polstat.ksa.repository.UserProfileRepository;
import com.polstat.ksa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final KabupatenRepository kabupatenRepository;

    @Autowired
    public UserProfileService(UserRepository userRepository, UserProfileRepository userProfileRepository, KabupatenRepository kabupatenRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.kabupatenRepository = kabupatenRepository;
    }

    public List<UserProfile> getAllProfiles() {
        return userProfileRepository.findAll();
    }

    public UserProfileDto getUserProfileByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + email);
        }

        UserProfile userProfile = user.getUserProfile();

        if (userProfile == null) {
            throw new UserProfileNotFoundException("UserProfile not found for User with email: " + email);
        }

        return UserProfileMapper.mapToUserProfileDto(userProfile);
    }

    public UserProfileDto updateUserProfile(String email, String firstName, String lastName, Long idKab) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + email);
        }

        UserProfile userProfile = user.getUserProfile();

        if (userProfile == null) {
            throw new UserProfileNotFoundException("UserProfile not found for User with email: " + email);
        }

        userProfile.setFirstName(firstName);
        userProfile.setLastName(lastName);

        Kabupaten kabupaten = kabupatenRepository.findById(idKab)
                .orElseThrow(() -> {
                    return new KabupatenNotFoundException(idKab);
                });

        userProfile.setKabupaten(kabupaten);

        userProfileRepository.save(userProfile);

        return UserProfileMapper.mapToUserProfileDto(userProfile);
    }
}
