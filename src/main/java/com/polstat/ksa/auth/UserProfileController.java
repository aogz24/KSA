package com.polstat.ksa.auth;

import com.polstat.ksa.auth.exception.UserNotFoundException;
import com.polstat.ksa.auth.exception.UserProfileNotFoundException;
import com.polstat.ksa.dto.UserProfileDto;
import com.polstat.ksa.entity.Kabupaten;
import com.polstat.ksa.entity.UserProfile;
import com.polstat.ksa.repository.KabupatenRepository;
import com.polstat.ksa.repository.UserProfileRepository;
import com.polstat.ksa.service.KabupatenNotFoundException;
import com.polstat.ksa.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class UserProfileController {
    private final UserProfileService userProfileService;
    private final KabupatenRepository kabupatenRepository;
    private final UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileController(UserProfileService userProfileService, KabupatenRepository kabupatenRepository, UserProfileRepository userProfileRepository) {
        this.userProfileService = userProfileService;
        this.kabupatenRepository = kabupatenRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @GetMapping("/profiles")
    public ResponseEntity<List<UserProfile>> getAllProfiles() {
        List<UserProfile> profiles = userProfileService.getAllProfiles();
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/profile/{email}")
    public ResponseEntity<UserProfileDto> getProfileByEmail(@PathVariable String email) {
            UserProfileDto profile = userProfileService.getUserProfileByEmail(email);
            return ResponseEntity.ok(profile);
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<?> updateProfileByEmail(@PathVariable String email, @RequestBody Map<String, String> requestBody) {
        try {
            Long idKab = Long.parseLong(requestBody.get("idKab"));
            String firstName = requestBody.get("firstName");
            String lastName = requestBody.get("lastName");

            UserProfileDto updatedProfile = userProfileService.updateUserProfile(email, firstName, lastName, idKab);

            return ResponseEntity.ok(updatedProfile);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body("User not found with email: " + email);
        } catch (UserProfileNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body("UserProfile not found for User with email: " + email);
        } catch (KabupatenNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body("Kabupaten not found with id: " + e.getIdKab());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating user profile.");
        }
    }

    @GetMapping("/userbykab/{nKab}")
    public ResponseEntity<?> getUserByKabupaten(@PathVariable String nKab) {
        try {
            Kabupaten kab = kabupatenRepository.findByNamaKabupatenIgnoreCase(nKab);
            if (kab == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kabupaten not found");
            }

            List<UserProfile> user = userProfileRepository.findByKabupatenIdCustomQuery(kab.getIdKab());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
