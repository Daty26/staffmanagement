package de.university.staffmanagement.service.impl;

import de.university.staffmanagement.dto.request.LeaveRequestDTO;
import de.university.staffmanagement.dto.request.LeaveStatusUpdateDTO;
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
/**
 * Implementation of the {@link de.university.staffmanagement.service.LeaveService} interface.
 *
 * <p>This service handles the creation, retrieval, status updates, and user-specific queries
 * for leave requests in the staff management system.
 *
 * <p>It uses {@link LeaveRepository} for persistence, {@link LeaveMapper} for DTO transformation,
 * and {@link NotificationService} to notify users about leave request events.
 *
 * <p>Main functionalities include:
 * <ul>
 *     <li>Creating a new leave request and notifying the user</li>
 *     <li>Fetching all leave requests or filtered ones by status/user</li>
 *     <li>Approving or rejecting leave requests and notifying the user</li>
 * </ul>
 *
 */
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

    /**
     * Creates a new leave request for the specified user and sets its status to PENDING.
     * A notification is also sent to the user confirming the request creation.
     *
     * @param leaveRequestDTO the data of the leave request (dates, type, reason)
     * @param user the user submitting the leave request
     * @return the saved leave request as a response DTO
     */
    @Override
    public LeaveResponseDTO create(LeaveRequestDTO leaveRequestDTO, User user) {
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

    /**
     * Retrieves all leave requests from the system.
     *
     * @return a list of all leave requests as DTOs
     */
    @Override
    public List<LeaveResponseDTO> getAll() {
        return leaveRepository.findAll()
                .stream()
                .map(leaveMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a leave request by its ID.
     *
     * @param id the ID of the leave request
     * @return the leave request as a DTO
     * @throws GeneralException if no leave request is found with the given ID
     */
    @Override
    public LeaveResponseDTO getReqById(Long id) {
        return leaveMapper.toDTO(leaveRepository.findById(id).orElseThrow(() -> new GeneralException("The request is not found")));
    }


    /**
     * Retrieves leave requests with a specific status for a specific user.
     *
     * @param status the status to filter by (e.g. PENDING, APPROVED)
     * @param userId the ID of the user
     * @return a list of filtered leave requests as DTOs
     * @throws RuntimeException if no user is found with the given ID
     */
    @Override
    public List<LeaveResponseDTO> getByStatus(Status status, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Id not found"));
        List<LeaveRequest> leaveRequests = leaveRepository.findByStatusAndUser(status, user);
        return leaveRequests.stream()
                .map(leaveMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates the status and manager comment of a leave request and sends a notification to the user.
     *
     * @param id the ID of the leave request
     * @param leaveStatusUpdateDTO contains the new status and an optional manager comment
     * @return the updated leave request as a DTO
     * @throws GeneralException if no leave request is found with the given ID
     */

    @Override
    public LeaveResponseDTO updateStatus(Long id, LeaveStatusUpdateDTO leaveStatusUpdateDTO) {
        LeaveRequest leaveRequest = leaveRepository.findById(id).orElseThrow(() -> new GeneralException("The request is not found"));
        System.out.println(leaveStatusUpdateDTO.getManagerComment());
        leaveRequest.setStatus(leaveStatusUpdateDTO.getNewStatus());
        leaveRequest.setManagerComment(leaveStatusUpdateDTO.getManagerComment());

        leaveRepository.save(leaveRequest);
        String msg = "Your request for " + leaveRequest.getLeaveType().toString().toLowerCase() + "request has been " + leaveStatusUpdateDTO.getNewStatus().toString().toLowerCase();
        notificationService.sendNotification(leaveRequest.getUser().getUsername(), msg);

        return leaveMapper.toDTO(leaveRequest);
    }
    /**
     * Retrieves all leave requests submitted by a specific user.
     *
     * @param userId the ID of the user
     * @return a list of leave requests belonging to the user
     * @throws GeneralException if the user is not found
     */
    @Override
    public List<LeaveResponseDTO> getReqByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException("User with this id was not found"));

        List<LeaveRequest> leaveRequests = leaveRepository.findByUser(user);

        return leaveRequests.stream()
                .map(leaveMapper::toDTO)
                .collect(Collectors.toList());
    }

}
