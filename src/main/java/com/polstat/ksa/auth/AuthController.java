package com.polstat.ksa.auth;


import com.polstat.ksa.auth.exception.UserNotFoundException;
import com.polstat.ksa.dto.UserDto;
import com.polstat.ksa.auth.exception.EmailAlreadyExistsException;
import com.polstat.ksa.entity.User;
import com.polstat.ksa.mapper.UserMapper;
import com.polstat.ksa.repository.UserRepository;
import com.polstat.ksa.service.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserService userService;

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );
            String accessToken = jwtUtil.generateAccessToken(authentication);
            AuthResponse response = new AuthResponse(request.getEmail(),
                    accessToken);
            return ResponseEntity.ok().body(response);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Terjadi Kesalahan saat login");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto request) {
        try {
            UserDto user = userService.createUser(request);
            return ResponseEntity.ok().body(user);
        } catch (EmailAlreadyExistsException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Email already exists");
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }



    @PutMapping("/password/{email}")
    public UserDto updateUserPassword(
            @PathVariable String email,
            @RequestBody PasswordChangeRequest passwordChangeRequest
    ) {
        return userService.updateUserPassword(email, passwordChangeRequest.getOldPassword(), passwordChangeRequest.getNewPassword());
    }

    @Getter
    public static class PasswordChangeRequest {
        private String name;
        private String oldPassword;
        private String newPassword;

        public void setName(String name) {
            this.name = name;
        }

        public void setOldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }

    @PutMapping("/update-role/{email}/admin")
    public ResponseEntity<UserDto> updateUserRoleToAdmin(@PathVariable String email) {
        try {
            UserDto updatedUser = userService.updateUserRoleToAdmin(email);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-role/{email}/pegawai")
    public ResponseEntity<?> updateUserRoleToPegawai(@PathVariable String email) {
        try {
            UserDto updatedUser = userService.updateUserRoleToPegawai(email);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid role");
        }
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Map<String, String>> deleteUserAndProfile(@PathVariable String email) {
        userService.deleteUserAndProfile(email);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User and profile deleted successfully.");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/findByToken/profile")
    public ResponseEntity<?> findProfileByToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            System.out.println(email);
            User userProfile = userRepository.findByEmail(email);

            if (userProfile != null) {
                return ResponseEntity.ok(userProfile);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Tidak terautoisasi");
    }

}

