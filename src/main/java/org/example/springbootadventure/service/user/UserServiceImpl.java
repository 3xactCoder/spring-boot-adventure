package org.example.springbootadventure.service.user;

import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.springbootadventure.dto.user.UserRegistrationRequestDto;
import org.example.springbootadventure.dto.user.UserResponseDto;
import org.example.springbootadventure.exceptions.EntityNotFoundException;
import org.example.springbootadventure.exceptions.RegistrationException;
import org.example.springbootadventure.mapper.UserMapper;
import org.example.springbootadventure.model.Role;
import org.example.springbootadventure.model.User;
import org.example.springbootadventure.repository.role.RoleRepository;
import org.example.springbootadventure.repository.user.UserRepository;
import org.example.springbootadventure.service.cart.ShoppingCartService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ShoppingCartService shoppingCartService;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException("User with email " + requestDto.getEmail()
                    + " already exists");
        }
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        Role defaultRole = roleRepository.findByName(Role.RoleName.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException("Default role "
                        + Role.RoleName.ROLE_USER + " not found"));
        user.setRoles(Set.of(defaultRole));

        User savedUser = userRepository.save(user);
        shoppingCartService.registerNewShoppingCart(user);
        return userMapper.toDto(savedUser);
    }
}


