package de.university.staffmanagement.service.impl;


import de.university.staffmanagement.enums.Role;
import de.university.staffmanagement.dto.request.UserRequestDTO;
import de.university.staffmanagement.dto.response.UserResponseDTO;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.exception.GeneralException;
import de.university.staffmanagement.mapper.UserMapper;
import de.university.staffmanagement.repository.UserRepository;
import de.university.staffmanagement.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO create(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new GeneralException("Username already exists");
        }

        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new GeneralException("Email already exists");
        }
        User user = userMapper.toEntity(userRequestDTO);
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new GeneralException("Password cannot be null or empty");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = userRequestDTO.getRole() != null ? userRequestDTO.getRole() : Role.EMPLOYEE;
        user.setRole(role);
        userRepository.save(user);

        return userMapper.toDTO(user);
    }

    @Override
    public UserResponseDTO update(User user, String username, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new GeneralException("User with such username exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new GeneralException("User with such email exists");
        }

        user.setUsername(username);
        user.setEmail(email);
        user.setEmail(email);
        userRepository.save(user);

        return userMapper.toDTO(user);
    }

    @Override
    public UserResponseDTO get(Long id) {
        return userMapper.toDTO(userRepository.findById(id).orElseThrow(() -> new GeneralException("User not found")));
    }

    @Override
    public List<UserResponseDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
}
