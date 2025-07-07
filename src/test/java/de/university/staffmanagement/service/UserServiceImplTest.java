package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.UserRequestDTO;
import de.university.staffmanagement.dto.response.UserResponseDTO;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.exception.GeneralException;
import de.university.staffmanagement.mapper.UserMapper;
import de.university.staffmanagement.repository.UserRepository;
import de.university.staffmanagement.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldCreateUser_whenValidRequest() {
        // Arrange
        UserRequestDTO request = new UserRequestDTO();
        request.setUsername("testuser");
        request.setPassword("password123");

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");

        User savedUser = new User();
        savedUser.setUsername("testuser");
        savedUser.setPassword("encoded-password");

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUsername("testuser");

        // Mock behavior
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userMapper.toEntity(request)).thenReturn(user);
        when(passwordEncoder.encode("password123")).thenReturn("encoded-password");
        when(userMapper.toDTO(any(User.class))).thenReturn(responseDTO);

        // Act
        UserResponseDTO result = userService.create(request);

        // Assert
        assertEquals("testuser", result.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void create_shouldThrowException_whenUsernameExists() {
        UserRequestDTO request = new UserRequestDTO();
        request.setUsername("duplicateUser");

        when(userRepository.existsByUsername("duplicateUser")).thenReturn(true);

        assertThrows(GeneralException.class, () -> userService.create(request));
    }

    @Test
    void create_shouldThrowException_whenPasswordIsEmpty() {
        UserRequestDTO request = new UserRequestDTO();
        request.setUsername("user");
        request.setPassword(""); // Empty

        when(userRepository.existsByUsername("user")).thenReturn(false);

        User user = new User();
        user.setPassword(""); // mapped entity

        when(userMapper.toEntity(request)).thenReturn(user);

        assertThrows(GeneralException.class, () -> userService.create(request));
    }
}

