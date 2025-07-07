package de.university.staffmanagement.dto.response;

import de.university.staffmanagement.enums.AttendanceStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DTO representing a daily summary of an employee's attendance.
 *
 * <p>Includes date, working hours, overtime, total time, and attendance status.
 */
@Data
public class EmployeeResponseDTO {
    private LocalDate date;
    private String startTime;
    private String endTime;
    private String regularHours;
    private String overtime;
    private String total;
    private AttendanceStatus status;
}

