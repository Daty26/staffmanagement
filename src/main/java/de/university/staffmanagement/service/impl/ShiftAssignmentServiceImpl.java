package de.university.staffmanagement.service.impl;

import de.university.staffmanagement.dto.request.ShiftAssignmentRequest;
import de.university.staffmanagement.dto.response.ShiftAssignmentResponse;
import de.university.staffmanagement.entity.ShiftAssignment;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.exception.GeneralException;
import de.university.staffmanagement.mapper.ShiftAssignmentMapper;
import de.university.staffmanagement.repository.ShiftAssignmentRepository;
import de.university.staffmanagement.repository.UserRepository;
import de.university.staffmanagement.service.ShiftAssignmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link de.university.staffmanagement.service.ShiftAssignmentService} interface.
 *
 * <p>This service manages the creation and retrieval of shift assignments
 * for users within the staff management system.
 *
 * <p>Main functionalities include:
 * <ul>
 *     <li>Creating a new shift assignment for a specific user</li>
 *     <li>Retrieving all shift assignments</li>
 *     <li>Retrieving all shift assignments for a specific user</li>
 * </ul>
 *
 * <p>Uses {@link ShiftAssignmentRepository} and {@link UserRepository} for database access,
 * and {@link ShiftAssignmentMapper} for converting between entities and DTOs.
 */
@Service
public class ShiftAssignmentServiceImpl implements ShiftAssignmentService {
    private final ShiftAssignmentRepository repository;
    private final UserRepository userRepository;
    private final ShiftAssignmentMapper mapper;



    public ShiftAssignmentServiceImpl(ShiftAssignmentRepository repository, UserRepository userRepository, ShiftAssignmentMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }
    /**
     * Creates a new shift assignment for a user based on the provided request data.
     *
     * @param request the shift assignment details (dates, times, type, user ID)
     * @return the created shift assignment as a response DTO
     * @throws GeneralException if the user specified in the request is not found
     */
    @Override
    public ShiftAssignmentResponse create(ShiftAssignmentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new GeneralException("User not found"));

        ShiftAssignment assignment = mapper.toEntity(request);
        assignment.setUser(user);

        ShiftAssignment saved = repository.save(assignment);
        String fullName = user.getUsername();
        return mapper.toDTO(saved, fullName);
    }

    /**
     * Retrieves all shift assignments stored in the system.
     *
     * @return a list of all shift assignments as response DTOs
     */
    @Override
    public List<ShiftAssignmentResponse> getAll() {
        return repository.findAll().stream()
                .map(shift -> mapper.toDTO(shift, shift.getUser().getUsername()))
                .collect(Collectors.toList());

    }

    /**
     * Retrieves all shift assignments for a specific user by user ID.
     *
     * @param userId the ID of the user
     * @return a list of the user's shift assignments as response DTOs
     * @throws GeneralException if the user is not found
     */
    @Override
    public List<ShiftAssignmentResponse> getByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException("User not found"));

        return repository.findByUser(user).stream()
                .map(shift -> {
                    String fullName = user.getUsername();
                    return mapper.toDTO(shift, fullName);
                })
                .collect(Collectors.toList());
    }


}
