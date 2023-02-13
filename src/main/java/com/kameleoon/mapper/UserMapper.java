package com.kameleoon.mapper;

import com.kameleoon.dto.user.UserInDTO;
import com.kameleoon.dto.user.UserOutDTO;
import com.kameleoon.entity.User;

public class UserMapper {

    public static UserOutDTO userToDto(User user) {
        return UserOutDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User dtoToUser(UserInDTO userInDTO) {
        return User.builder()
                .name(userInDTO.getName())
                .email(userInDTO.getEmail())
                .build();
    }
}
