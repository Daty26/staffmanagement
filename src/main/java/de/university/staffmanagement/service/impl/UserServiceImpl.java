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

/**
 * Implementation of the {@link de.university.staffmanagement.service.UserService} interface.
 *
 * <p>This service handles user creation, updating, and retrieval within the staff management system.
 *
 * <p>Main functionalities include:
 * <ul>
 *     <li>Registering new users with unique usernames and emails</li>
 *     <li>Updating user credentials (username/email)</li>
 *     <li>Fetching user information individually or as a list</li>
 * </ul>
 *
 * <p>Uses {@link UserRepository} for persistence,
 * {@link UserMapper} for entity-DTO transformation,
 * and {@link PasswordEncoder} for securing passwords.
 */
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

    /**
     * Creates a new user based on the provided request DTO.
     * The password is encrypted and the default role is set to EMPLOYEE unless specified.
     *
     * @param userRequestDTO the user's registration information
     * @return the created user as a response DTO
     * @throws GeneralException if the username or email already exists, or the password is empty
     */
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

    /**
     * Updates the username and email of an existing user.
     *
     * @param user the existing user entity to update
     * @param username the new username to assign
     * @param email the new email to assign
     * @return the updated user as a response DTO
     * @throws GeneralException if the new username or email already exists
     */
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
        userRepository.save(user);

        return userMapper.toDTO(user);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user
     * @return the user as a response DTO
     * @throws GeneralException if the user is not found
     */
    @Override
    public UserResponseDTO get(Long id) {
        return userMapper.toDTO(userRepository.findById(id).orElseThrow(() -> new GeneralException("User not found")));
    }

    /**
     * Retrieves all users in the system.
     *
     * @return a list of all users as response DTOs
     */
    @Override
    public List<UserResponseDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
}
