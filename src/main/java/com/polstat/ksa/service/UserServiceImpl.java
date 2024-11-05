package com.polstat.ksa.service;

import com.polstat.ksa.auth.JwtUtil;
import com.polstat.ksa.auth.exception.EmailAlreadyExistsException;
import com.polstat.ksa.auth.exception.UserNotFoundException;
import com.polstat.ksa.dto.UserDto;
import com.polstat.ksa.dto.UserProfileDto;
import com.polstat.ksa.entity.UserRole;
import com.polstat.ksa.repository.UserProfileRepository;
import com.polstat.ksa.entity.User;
import com.polstat.ksa.entity.UserProfile;
import com.polstat.ksa.mapper.UserMapper;
import com.polstat.ksa.mapper.UserProfileMapper;
import com.polstat.ksa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private JwtUtil jwtUtil;
    public UserDto createUser(UserDto userDto) throws EmailAlreadyExistsException {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setRole(String.valueOf(UserRole.USER));
        try {
            User user = userRepository.save(UserMapper.mapToUser(userDto));

            UserProfileDto userProfileDto = new UserProfileDto();
            userProfileDto.setFirstName(user.getName());

            UserProfile userProfile = UserProfileMapper.mapToUserProfile(userProfileDto);
            userProfile.setUser(user);
            userProfileRepository.save(userProfile);

            return UserMapper.mapToUserDto(user);
        } catch (DataIntegrityViolationException e) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
    }
    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        UserDto userDto = UserMapper.mapToUserDto(user);

        if (user != null) {
            userDto.setRole(String.valueOf(user.getRole()));
        }

        return userDto;
    }
    @Override
    public UserDto updateUserPassword(String email, String oldPassword, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto updateUserRoleToAdmin(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        try {
            user.setRole(UserRole.ADMIN);
            userRepository.save(user);
            return UserMapper.mapToUserDto(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Invalid role");
        }
    }

    @Override
    public UserDto updateUserRoleToPegawai(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        try {
            user.setRole(UserRole.PEGAWAI);
            userRepository.save(user);
            return UserMapper.mapToUserDto(user);
        } catch (DataIntegrityViolationException e) {
            String errorMessage = "Error while updating role: " + e.getMessage();
            throw new IllegalArgumentException(errorMessage);
        }
    }


    @Override
    public void deleteUserAndProfile(String email) {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            UserProfile userProfile = user.getUserProfile();
            if (userProfile != null) {
                userProfileRepository.delete(userProfile);
            }
            userRepository.delete(user);
        }
    }

    public UserDto findProfileByToken(String token) {
        if (jwtUtil.validateAccessToken(token)) {
            String username = jwtUtil.getSubject(token);
            User userProfile = userRepository.findByEmail(username);
            UserDto user1 = UserMapper.mapToUserDto(userProfile);
            return user1;
        } else {
            return null;
        }
    }


}
