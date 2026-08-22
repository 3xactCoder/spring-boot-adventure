package org.example.springbootadventure.mapper.impl;

import javax.annotation.processing.Generated;
import org.example.springbootadventure.dto.user.UserRegistrationRequestDto;
import org.example.springbootadventure.dto.user.UserResponseDto;
import org.example.springbootadventure.mapper.UserMapper;
import org.example.springbootadventure.model.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-22T17:59:57+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();

        if ( user.getId() != null ) {
            userResponseDto.setId( user.getId() );
        }
        if ( user.getEmail() != null ) {
            userResponseDto.setEmail( user.getEmail() );
        }
        if ( user.getFirstName() != null ) {
            userResponseDto.setFirstName( user.getFirstName() );
        }
        if ( user.getLastName() != null ) {
            userResponseDto.setLastName( user.getLastName() );
        }
        if ( user.getShippingAddress() != null ) {
            userResponseDto.setShippingAddress( user.getShippingAddress() );
        }

        return userResponseDto;
    }

    @Override
    public User toModel(UserRegistrationRequestDto requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        User user = new User();

        if ( requestDto.getEmail() != null ) {
            user.setEmail( requestDto.getEmail() );
        }
        if ( requestDto.getPassword() != null ) {
            user.setPassword( requestDto.getPassword() );
        }
        if ( requestDto.getFirstName() != null ) {
            user.setFirstName( requestDto.getFirstName() );
        }
        if ( requestDto.getLastName() != null ) {
            user.setLastName( requestDto.getLastName() );
        }
        if ( requestDto.getShippingAddress() != null ) {
            user.setShippingAddress( requestDto.getShippingAddress() );
        }

        return user;
    }
}
