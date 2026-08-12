package org.example.springbootadventure.service.user;

import lombok.RequiredArgsConstructor;

import org.example.springbootadventure.dto.user.UserRegistrationRequestDto;
import org.example.springbootadventure.dto.user.UserResponseDto;
import org.example.springbootadventure.exceptions.RegistrationException;
import org.example.springbootadventure.mapper.UserMapper;
import org.example.springbootadventure.model.User;

import org.example.springbootadventure.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException("Can't register user, email already exists: "
                    + requestDto.getEmail());
        }

        User user = userMapper.toModel(requestDto);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}