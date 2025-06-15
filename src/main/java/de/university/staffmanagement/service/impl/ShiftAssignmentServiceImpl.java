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

    @Override
    public List<ShiftAssignmentResponse> getAll() {
        return repository.findAll().stream()
                .map(shift -> {
                    String fullName = shift.getUser().getUsername();
                    return mapper.toDTO(shift, fullName);
                })
                .collect(Collectors.toList());
    }

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
