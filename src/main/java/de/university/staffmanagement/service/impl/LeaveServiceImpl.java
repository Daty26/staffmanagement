package de.university.staffmanagement.service.impl;

import de.university.staffmanagement.dto.request.LeaveRequestDTO;
import de.university.staffmanagement.dto.request.NotificationRequestDTO;
import de.university.staffmanagement.dto.response.LeaveResponseDTO;
import de.university.staffmanagement.dto.response.NotificationResponseDTO;
import de.university.staffmanagement.entity.LeaveRequest;
import de.university.staffmanagement.entity.PersonalInfo;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.enums.Status;
import de.university.staffmanagement.exception.GeneralException;
import de.university.staffmanagement.mapper.LeaveMapper;
import de.university.staffmanagement.repository.LeaveRepository;
import de.university.staffmanagement.repository.PersonalInfoRepository;
import de.university.staffmanagement.repository.UserRepository;
import de.university.staffmanagement.service.LeaveService;
import de.university.staffmanagement.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveServiceImpl implements LeaveService {
    private final LeaveRepository leaveRepository;
    private final LeaveMapper leaveMapper;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final PersonalInfoRepository personalInfoRepository;

    public LeaveServiceImpl(LeaveRepository leaveRepository, LeaveMapper leaveMapper, UserRepository userRepository, NotificationService notificationService, PersonalInfoRepository personalInfoRepository) {
        this.leaveRepository = leaveRepository;
        this.leaveMapper = leaveMapper;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.personalInfoRepository = personalInfoRepository;
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

        String msg = "Your leave request for " + leaveRequest.getLeaveType().toString().toLowerCase() + " leave from " +leaveRequest.getStartDate()+ " to "+leaveRequest.getEndDate()+" has been created and is pending approval";
        notificationService.sendNotification(user.getUsername(), msg);
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
        String msg = "Your request for " + leaveRequest.getLeaveType().toString().toLowerCase() + " has been " + newStatus.toString().toLowerCase();
        notificationService.sendNotification(leaveRequest.getUser().getUsername(), msg);

        return leaveMapper.toDTO(updatedRequest);
    }

    @Override
    public List<LeaveResponseDTO> getReqByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException("User with this id was not found"));

        PersonalInfo personalInfo = personalInfoRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Personal info was not found for this user"));



        List<LeaveRequest> leaveRequests = leaveRepository.findByUser(user);

        return leaveRequests.stream()
                .map(leaveRequest -> {
                    LeaveResponseDTO dto = leaveMapper.toDTO(leaveRequest);
                    dto.setFullname(personalInfo.getFullName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

}
