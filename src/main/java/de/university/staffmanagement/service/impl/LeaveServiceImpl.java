package de.university.staffmanagement.service.impl;

import de.university.staffmanagement.dto.request.LeaveRequestDTO;
import de.university.staffmanagement.dto.response.LeaveResponseDTO;
import de.university.staffmanagement.entity.LeaveRequest;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.enums.Status;
import de.university.staffmanagement.exception.GeneralException;
import de.university.staffmanagement.mapper.LeaveMapper;
import de.university.staffmanagement.repository.LeaveRepository;
import de.university.staffmanagement.repository.UserRepository;
import de.university.staffmanagement.service.LeaveService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveServiceImpl implements LeaveService {
    private final LeaveRepository leaveRepository;
    private final LeaveMapper leaveMapper;
    private final UserRepository userRepository;

    public LeaveServiceImpl(LeaveRepository leaveRepository, LeaveMapper leaveMapper, UserRepository userRepository) {
        this.leaveRepository = leaveRepository;
        this.leaveMapper = leaveMapper;
        this.userRepository = userRepository;
    }


    @Override
    public LeaveResponseDTO create(LeaveRequestDTO leaveRequestDTO) {
        User user = userRepository.findById(leaveRequestDTO.getUserId())
                .orElseThrow(() -> new GeneralException("User not found"));
        LeaveRequest leaveRequest = leaveMapper.toEntity(leaveRequestDTO);
//        System.out.println("the req crtd: " + leaveRequest.getLeaveType()   + leaveRequest.getStartDate() + leaveRequest.getEndDate()  + leaveRequest.getReason());
        //we already checked for null and empty values on the frontend side
        leaveRequest.setStatus(Status.PENDING);
        leaveRequest.setUser(user);


        LeaveRequest savedRequest = leaveRepository.save(leaveRequest);
        return leaveMapper.toDTO(savedRequest);
    }


    @Override
    public List<LeaveResponseDTO> getAll() {
        return leaveRepository.findAll()
                .stream()
                .map(leaveMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LeaveResponseDTO getReqById(Long id) {
        return leaveMapper.toDTO(leaveRepository.findById(id).orElseThrow(() -> new GeneralException("The request is not found")));
    }


    @Override
    public List<LeaveResponseDTO> getByStatus(Status status) {
        List<LeaveRequest> leaveRequests = leaveRepository.findByStatus(status);

        return leaveRequests.stream()
                .map(leaveMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LeaveResponseDTO updateStatus(Long id, Status newStatus) {
        LeaveRequest leaveRequest = leaveRepository.findById(id).orElseThrow(() -> new GeneralException("The request is not found"));

        leaveRequest.setStatus(newStatus);
        LeaveRequest updatedRequest = leaveRepository.save(leaveRequest);

        return leaveMapper.toDTO(updatedRequest);
    }

    @Override
    public List<LeaveResponseDTO> getReqByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException("User with this id was not found "));
        List<LeaveRequest> leaveRequests = leaveRepository.findByUser(user);
        return leaveRequests.stream()
                .map(leaveMapper::toDTO)
                .collect(Collectors.toList());


    }

}
