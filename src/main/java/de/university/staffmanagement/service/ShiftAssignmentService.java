package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.ShiftAssignmentRequest;
import de.university.staffmanagement.dto.response.ShiftAssignmentResponse;

import java.util.List;

public interface ShiftAssignmentService {
    ShiftAssignmentResponse create(ShiftAssignmentRequest request);
    List<ShiftAssignmentResponse> getAll();
    List<ShiftAssignmentResponse> getByUserId(Long userId);
}
