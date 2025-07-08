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


/**
 * Service implementation for generating employee weekly attendance logs.
 *
 * <p>This service aggregates data from:
 * <ul>
 *     <li>{@link ClockEntry} - employee clock-in/out records</li>
 *     <li>{@link ShiftAssignment} - scheduled shifts</li>
 *     <li>{@link LeaveRequest} - approved leave requests</li>
 * </ul>
 *
 * <p>It provides managers with a daily breakdown of an employee’s attendance status,
 * total hours worked, overtime, and leave status across a given week.
 *
 * <p>Used in manager views for analyzing staff attendance and performance.
 *
 * @see EmployeeResponseDTO
 * @see EmployeeLogsService
 */
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
    /**
     * Generates a weekly overview of an employee’s attendance status, working hours,
     * and shift information based on clock-in/out entries, shift assignments, and approved leaves.
     *
     * <p>The result contains 7 days starting from {@code weekStart}, and determines for each day:
     * <ul>
     *     <li>Clock-in/out times (if present)</li>
     *     <li>Expected working hours (from shift assignment)</li>
     *     <li>Actual worked hours</li>
     *     <li>Overtime (if any)</li>
     *     <li>Attendance status: REGULAR, ON_LEAVE, ABSENT, INSUFFICIENT</li>
     * </ul>
     *
     * @param userId the ID of the user (employee) whose weekly logs are being requested
     * @param weekStart the starting date of the week (usually a Monday)
     * @return a list of {@link EmployeeResponseDTO} objects, one for each day of the specified week
     * @throws GeneralException if the user with the given ID does not exist
     */
    @Override
    public List<EmployeeResponseDTO> getWeeklyOverview(Long userId, LocalDate weekStart) {
        LocalDate weekEnd = weekStart.plusDays(6);

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException("User not found"));

        List<ClockEntry> clockEntries = this.clockEntryRepository
                .findAllByUserAndClockInTimeBetween(user, weekStart.atStartOfDay(), weekEnd.plusDays(1).atStartOfDay());

        List<ShiftAssignment> shifts = this.shiftAssignmentRepository
                .findByUserAndShiftDateBetween(user, weekStart, weekEnd);

        List<LeaveRequest> leaves = this.leaveRepository
                .findApprovedByUserAndDateRange(user, weekStart, weekEnd);

        Map<LocalDate, ClockEntry> clockMap = clockEntries.stream()
                .collect(Collectors.toMap(
                        entry -> entry.getClockInTime().toLocalDate(),
                        entry -> entry,
                        (existing, replacement) -> existing // or replacement
                ));

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
