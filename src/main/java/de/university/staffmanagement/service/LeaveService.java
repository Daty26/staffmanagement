package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.LeaveRequestDTO;
import de.university.staffmanagement.dto.request.LeaveStatusUpdateDTO;
import de.university.staffmanagement.dto.response.LeaveResponseDTO;
import de.university.staffmanagement.entity.LeaveRequest;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.enums.Status;

import java.util.List;

public interface LeaveService {
    LeaveResponseDTO create(LeaveRequestDTO leaveRequestDTO, User user);
    List<LeaveResponseDTO> getAll();
    LeaveResponseDTO getReqById(Long id);
    List<LeaveResponseDTO> getByStatus(Status status, Long userId);
    LeaveResponseDTO updateStatus(Long id, LeaveStatusUpdateDTO leaveStatusUpdateDTO);
    List<LeaveResponseDTO> getReqByUserId(Long userId);

}
