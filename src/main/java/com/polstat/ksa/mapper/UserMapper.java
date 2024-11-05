package com.polstat.ksa.mapper;


import com.polstat.ksa.dto.UserDto;
import com.polstat.ksa.entity.User;
import com.polstat.ksa.entity.UserRole;

public class UserMapper {
    public static User mapToUser(UserDto userDto){
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(UserRole.valueOf(userDto.getRole()))
                .build();
    }
    public static UserDto mapToUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(String.valueOf(user.getRole()))
                .build();
    }
}
