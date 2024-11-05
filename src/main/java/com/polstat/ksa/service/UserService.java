package com.polstat.ksa.service;


import com.polstat.ksa.auth.exception.EmailAlreadyExistsException;
import com.polstat.ksa.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public UserDto createUser(UserDto user) throws EmailAlreadyExistsException;
    public UserDto getUserByEmail(String email);
    public UserDto updateUserPassword(String email, String oldPassword, String newPassword);

    UserDto updateUserRoleToAdmin(String email);

    public void deleteUserAndProfile(String email);
    public UserDto updateUserRoleToPegawai(String email);
    public UserDto findProfileByToken(String token);
}

