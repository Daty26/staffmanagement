package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.ShiftAssignmentRequest;
import de.university.staffmanagement.dto.response.ShiftAssignmentResponse;
import de.university.staffmanagement.entity.ShiftAssignment;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.exception.GeneralException;
import de.university.staffmanagement.mapper.ShiftAssignmentMapper;
import de.university.staffmanagement.repository.ShiftAssignmentRepository;
import de.university.staffmanagement.repository.UserRepository;
import de.university.staffmanagement.service.impl.ShiftAssignmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShiftAssignmentServiceImplTest {

    @Mock
    private ShiftAssignmentRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShiftAssignmentMapper mapper;

    @InjectMocks
    private ShiftAssignmentServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldReturnResponse_whenValidRequest() {
        // Arrange
        ShiftAssignmentRequest request = new ShiftAssignmentRequest();
        request.setUserId(1L);

        User user = new User();
        user.setUsername("alice");

        ShiftAssignment assignment = new ShiftAssignment();
        assignment.setUser(user);

        ShiftAssignment savedAssignment = new ShiftAssignment();
        savedAssignment.setUser(user);

        ShiftAssignmentResponse response = new ShiftAssignmentResponse();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mapper.toEntity(request)).thenReturn(assignment);
        when(repository.save(assignment)).thenReturn(savedAssignment);
        when(mapper.toDTO(savedAssignment, "alice")).thenReturn(response);

        // Act
        ShiftAssignmentResponse result = service.create(request);

        // Assert
        verify(repository).save(assignment);
    }

    @Test
    void create_shouldThrow_whenUserNotFound() {
        ShiftAssignmentRequest request = new ShiftAssignmentRequest();
        request.setUserId(99L);

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(GeneralException.class, () -> service.create(request));
    }

    @Test
    void getAll_shouldReturnListOfResponses() {
        User user = new User();
        user.setUsername("bob");

        ShiftAssignment assignment1 = new ShiftAssignment();
        assignment1.setUser(user);

        ShiftAssignment assignment2 = new ShiftAssignment();
        assignment2.setUser(user);

        when(repository.findAll()).thenReturn(Arrays.asList(assignment1, assignment2));
        when(mapper.toDTO(any(), eq("bob")))
                .thenReturn(new ShiftAssignmentResponse());

        List<ShiftAssignmentResponse> results = service.getAll();

        assertEquals(2, results.size());
    }


    @Test
    void getByUserId_shouldThrow_whenUserNotFound() {
        when(userRepository.findById(404L)).thenReturn(Optional.empty());

        assertThrows(GeneralException.class, () -> service.getByUserId(404L));
    }
}