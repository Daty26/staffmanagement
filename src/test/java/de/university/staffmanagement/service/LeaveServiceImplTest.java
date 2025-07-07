package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.LeaveRequestDTO;
import de.university.staffmanagement.dto.request.LeaveStatusUpdateDTO;
import de.university.staffmanagement.dto.response.LeaveResponseDTO;
import de.university.staffmanagement.entity.LeaveRequest;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.enums.Status;
import de.university.staffmanagement.exception.GeneralException;
import de.university.staffmanagement.mapper.LeaveMapper;
import de.university.staffmanagement.repository.LeaveRepository;
import de.university.staffmanagement.repository.PersonalInfoRepository;
import de.university.staffmanagement.repository.UserRepository;
import de.university.staffmanagement.service.NotificationService;
import de.university.staffmanagement.service.impl.LeaveServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LeaveServiceImplTest {

    @Mock
    private LeaveRepository leaveRepository;

    @Mock
    private LeaveMapper leaveMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private PersonalInfoRepository personalInfoRepository;

    @InjectMocks
    private LeaveServiceImpl leaveService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void getAll_shouldReturnMappedList() {
        LeaveRequest req1 = new LeaveRequest();
        LeaveRequest req2 = new LeaveRequest();

        when(leaveRepository.findAll()).thenReturn(List.of(req1, req2));
        when(leaveMapper.toDTO(any())).thenReturn(new LeaveResponseDTO());

        List<LeaveResponseDTO> result = leaveService.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void getReqById_shouldReturnDTO_ifFound() {
        LeaveRequest leave = new LeaveRequest();

        when(leaveRepository.findById(1L)).thenReturn(Optional.of(leave));
        when(leaveMapper.toDTO(leave)).thenReturn(new LeaveResponseDTO());

        LeaveResponseDTO result = leaveService.getReqById(1L);

        assertNotNull(result);
    }

    @Test
    void getReqById_shouldThrow_ifNotFound() {
        when(leaveRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(GeneralException.class, () -> leaveService.getReqById(99L));
    }

    @Test
    void getByStatus_shouldReturnList_ifUserExists() {
        User user = new User();
        user.setUserId(10L);

        LeaveRequest leave = new LeaveRequest();
        leave.setUser(user);

        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
        when(leaveRepository.findByStatusAndUser(Status.PENDING, user)).thenReturn(List.of(leave));
        when(leaveMapper.toDTO(leave)).thenReturn(new LeaveResponseDTO());

        List<LeaveResponseDTO> result = leaveService.getByStatus(Status.PENDING, 10L);

        assertEquals(1, result.size());
    }

    @Test
    void getByStatus_shouldThrow_ifUserNotFound() {
        when(userRepository.findById(404L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> leaveService.getByStatus(Status.PENDING, 404L));
    }


    @Test
    void updateStatus_shouldThrow_ifRequestNotFound() {
        when(leaveRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(GeneralException.class, () -> leaveService.updateStatus(99L, new LeaveStatusUpdateDTO()));
    }

    @Test
    void getReqByUserId_shouldReturnList_ifUserExists() {
        User user = new User();
        user.setUserId(12L);

        LeaveRequest leave = new LeaveRequest();
        leave.setUser(user);

        when(userRepository.findById(12L)).thenReturn(Optional.of(user));
        when(leaveRepository.findByUser(user)).thenReturn(List.of(leave));
        when(leaveMapper.toDTO(leave)).thenReturn(new LeaveResponseDTO());

        List<LeaveResponseDTO> result = leaveService.getReqByUserId(12L);

        assertEquals(1, result.size());
    }

    @Test
    void getReqByUserId_shouldThrow_ifUserNotFound() {
        when(userRepository.findById(401L)).thenReturn(Optional.empty());

        assertThrows(GeneralException.class, () -> leaveService.getReqByUserId(401L));
    }
}
