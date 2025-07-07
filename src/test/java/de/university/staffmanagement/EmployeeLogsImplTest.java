package de.university.staffmanagement;

import de.university.staffmanagement.dto.response.EmployeeResponseDTO;
import de.university.staffmanagement.entity.ClockEntry;
import de.university.staffmanagement.entity.LeaveRequest;
import de.university.staffmanagement.entity.ShiftAssignment;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.enums.AttendanceStatus;
import de.university.staffmanagement.exception.GeneralException;
import de.university.staffmanagement.repository.ClockEntryRepository;
import de.university.staffmanagement.repository.LeaveRepository;
import de.university.staffmanagement.repository.ShiftAssignmentRepository;
import de.university.staffmanagement.repository.UserRepository;
import de.university.staffmanagement.service.impl.EmployeeLogsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeLogsImplTest {

    @Mock private UserRepository userRepository;
    @Mock private ClockEntryRepository clockEntryRepository;
    @Mock private ShiftAssignmentRepository shiftAssignmentRepository;
    @Mock private LeaveRepository leaveRepository;

    @InjectMocks
    private EmployeeLogsImpl employeeLogsService;

    private User testUser;
    private LocalDate weekStart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User();
        testUser.setUserId(1L);
        weekStart = LocalDate.of(2024, 4, 1); // Monday
    }

    @Test
    void getWeeklyOverview_shouldReturn7Days() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(clockEntryRepository.findAllByUserAndClockInTimeBetween(any(), any(), any())).thenReturn(Collections.emptyList());
        when(shiftAssignmentRepository.findByUserAndShiftDateBetween(any(), any(), any())).thenReturn(Collections.emptyList());
        when(leaveRepository.findApprovedByUserAndDateRange(any(), any(), any())).thenReturn(Collections.emptyList());

        List<EmployeeResponseDTO> result = employeeLogsService.getWeeklyOverview(1L, weekStart);

        assertEquals(7, result.size());
        assertTrue(result.stream().allMatch(r -> r.getStatus() == AttendanceStatus.ABSENT));
    }

    @Test
    void getWeeklyOverview_shouldSetStatusToOnLeave_whenLeaveExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        LeaveRequest leave = new LeaveRequest();
        leave.setStartDate(weekStart.plusDays(2));
        leave.setEndDate(weekStart.plusDays(2));

        when(clockEntryRepository.findAllByUserAndClockInTimeBetween(any(), any(), any())).thenReturn(Collections.emptyList());
        when(shiftAssignmentRepository.findByUserAndShiftDateBetween(any(), any(), any())).thenReturn(Collections.emptyList());
        when(leaveRepository.findApprovedByUserAndDateRange(any(), any(), any())).thenReturn(List.of(leave));

        List<EmployeeResponseDTO> result = employeeLogsService.getWeeklyOverview(1L, weekStart);

        assertEquals(AttendanceStatus.ON_LEAVE, result.get(2).getStatus());
    }

    @Test
    void getWeeklyOverview_shouldSetStatusToRegular_whenClockMatchesShift() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        LocalDateTime in = weekStart.atTime(9, 0);
        LocalDateTime out = in.plusHours(8);

        ClockEntry clockEntry = new ClockEntry();
        clockEntry.setClockInTime(in);
        clockEntry.setClockOutTime(out);
        clockEntry.setUser(testUser);

        ShiftAssignment shift = new ShiftAssignment();
        shift.setShiftDate(weekStart);
        shift.setStartTime(LocalTime.of(9, 0));
        shift.setEndTime(LocalTime.of(17, 0));

        when(clockEntryRepository.findAllByUserAndClockInTimeBetween(any(), any(), any()))
                .thenReturn(List.of(clockEntry));
        when(shiftAssignmentRepository.findByUserAndShiftDateBetween(any(), any(), any()))
                .thenReturn(List.of(shift));
        when(leaveRepository.findApprovedByUserAndDateRange(any(), any(), any()))
                .thenReturn(Collections.emptyList());

        List<EmployeeResponseDTO> result = employeeLogsService.getWeeklyOverview(1L, weekStart);

        EmployeeResponseDTO day1 = result.get(0);
        assertEquals(AttendanceStatus.REGULAR, day1.getStatus());
        assertEquals("8h", day1.getTotal());
        assertEquals("0h", day1.getOvertime());
    }

    @Test
    void getWeeklyOverview_shouldThrow_whenUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(GeneralException.class, () -> {
            employeeLogsService.getWeeklyOverview(999L, LocalDate.now());
        });
    }
}
