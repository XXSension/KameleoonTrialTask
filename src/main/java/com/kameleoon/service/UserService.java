package com.kameleoon.service;

import com.kameleoon.dto.user.UserInDTO;
import com.kameleoon.dto.user.UserOutDTO;
import com.kameleoon.entity.User;
import com.kameleoon.exception.ConflictException;
import com.kameleoon.mapper.UserMapper;
import com.kameleoon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.DataException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kameleoon.exception.ExceptionDescriptions.USER_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserOutDTO createUser(UserInDTO userInDTO) {
        try {
            return UserMapper.userToDto(userRepository.save(UserMapper.dtoToUser(userInDTO)));
        } catch (DataException dataException) {
            throw new ConflictException(USER_ALREADY_EXISTS.getTitle());
        }
    }
}
