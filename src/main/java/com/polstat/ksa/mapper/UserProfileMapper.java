package com.polstat.ksa.mapper;

import com.polstat.ksa.dto.UserProfileDto;
import com.polstat.ksa.entity.UserProfile;

public class UserProfileMapper {
    public static UserProfile mapToUserProfile(UserProfileDto userProfileDto) {
        return UserProfile.builder()
                .id(userProfileDto.getId())
                .firstName(userProfileDto.getFirstName())
                .lastName(userProfileDto.getLastName())
                .build();
    }

    public static UserProfileDto mapToUserProfileDto(UserProfile userProfile) {
        return UserProfileDto.builder()
                .id(userProfile.getId())
                .firstName(userProfile.getFirstName())
                .lastName(userProfile.getLastName())
                .idKab(userProfile.getKabupaten().getIdKab())
                .build();
    }
}

