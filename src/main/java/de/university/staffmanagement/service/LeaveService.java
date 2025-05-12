package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.LeaveRequestDTO;
import de.university.staffmanagement.dto.response.LeaveResponseDTO;
import de.university.staffmanagement.entity.LeaveRequest;
import de.university.staffmanagement.enums.Status;

import java.util.List;

public interface LeaveService {
    LeaveResponseDTO create(LeaveRequestDTO leaveRequestDTO);
    List<LeaveResponseDTO> getAll();
    LeaveResponseDTO getReqById(Long id);
    List<LeaveResponseDTO> getByStatus(Status status);
    LeaveResponseDTO updateStatus(Long id, Status newStatus);
    List<LeaveResponseDTO> getReqByUserId(Long userId);

}
