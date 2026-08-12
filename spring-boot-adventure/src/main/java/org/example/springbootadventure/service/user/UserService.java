package org.example.springbootadventure.service.user;


import org.example.springbootadventure.dto.user.UserRegistrationRequestDto;
import org.example.springbootadventure.dto.user.UserResponseDto;
import org.example.springbootadventure.exceptions.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException;
}