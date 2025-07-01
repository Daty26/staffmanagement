package de.university.staffmanagement.service.impl;


import de.university.staffmanagement.dto.request.EmployeeRequestDTO;
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
import de.university.staffmanagement.service.EmployeeLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class EmployeeLogsImpl implements EmployeeLogsService {

    private final UserRepository userRepository;
    private final ClockEntryRepository clockEntryRepository;
    private final ShiftAssignmentRepository shiftAssignmentRepository;
    private final LeaveRepository leaveRepository;

    @Autowired
    public EmployeeLogsImpl(UserRepository userRepository,
                            ClockEntryRepository clockEntryRepository,
                            ShiftAssignmentRepository shiftAssignmentRepository,
                            LeaveRepository leaveRepository) {
        this.userRepository = userRepository;
        this.clockEntryRepository = clockEntryRepository;
        this.shiftAssignmentRepository = shiftAssignmentRepository;
        this.leaveRepository = leaveRepository;
    }

    @Override
    public List<EmployeeResponseDTO> getWeeklyOverview(EmployeeRequestDTO request) {
        LocalDate weekStart = request.getWeekStart();
        LocalDate weekEnd = weekStart.plusDays(6);
        Long userId = request.getUserId();

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException("User not found"));

        List<ClockEntry> clockEntries = this.clockEntryRepository
                .findAllByUserAndClockInTimeBetween(user, weekStart.atStartOfDay(), weekEnd.plusDays(1).atStartOfDay());

        List<ShiftAssignment> shifts = this.shiftAssignmentRepository
                .findByUserAndShiftDateBetween(user, weekStart, weekEnd);

        List<LeaveRequest> leaves = this.leaveRepository
                .findApprovedByUserAndDateRange(user, weekStart, weekEnd);

        Map<LocalDate, ClockEntry> clockMap = clockEntries.stream()
                .collect(Collectors.toMap(e -> e.getClockInTime().toLocalDate(), e -> e));

        Map<LocalDate, ShiftAssignment> shiftMap = shifts.stream()
                .collect(Collectors.toMap(ShiftAssignment::getShiftDate, s -> s));

        Set<LocalDate> leaveDates = leaves.stream()
                .flatMap(lr -> lr.getStartDate().datesUntil(lr.getEndDate().plusDays(1)))
                .collect(Collectors.toSet());

        List<EmployeeResponseDTO> overview = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            LocalDate day = weekStart.plusDays(i);
            EmployeeResponseDTO dto = new EmployeeResponseDTO();
            dto.setDate(day);

            ShiftAssignment shift = shiftMap.get(day);
            ClockEntry clock = clockMap.get(day);

            if (leaveDates.contains(day)) {
                dto.setStatus(AttendanceStatus.ON_LEAVE);
            } else if (clock != null) {
                String start = clock.getClockInTime().toLocalTime().toString();
                String end = clock.getClockOutTime() != null
                        ? clock.getClockOutTime().toLocalTime().toString()
                        : "-";

                dto.setStartTime(start);
                dto.setEndTime(end);

                long minutesWorked = clock.getClockOutTime() != null
                        ? Duration.between(clock.getClockInTime(), clock.getClockOutTime()).toMinutes()
                        : 0;

                long regularMinutes = shift != null
                        ? Duration.between(shift.getStartTime(), shift.getEndTime()).toMinutes()
                        : 480;

                dto.setRegularHours((regularMinutes / 60) + "h");
                dto.setOvertime(((Math.max(0, minutesWorked - regularMinutes)) / 60) + "h");
                dto.setTotal((minutesWorked / 60) + "h");

                dto.setStatus(minutesWorked >= regularMinutes
                        ? AttendanceStatus.REGULAR
                        : AttendanceStatus.INSUFFICIENT);
            } else {
                dto.setStatus(AttendanceStatus.ABSENT);
                dto.setStartTime("-");
                dto.setEndTime("-");
                dto.setRegularHours("0h");
                dto.setOvertime("0h");
                dto.setTotal("0h");
            }

            overview.add(dto);
        }

        return overview;
    }
}
