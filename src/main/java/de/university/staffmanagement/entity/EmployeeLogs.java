package de.university.staffmanagement.entity;

import de.university.staffmanagement.enums.AttendanceStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Model representing a summarized view of an employee's daily attendance log.
 *
 * <p>Includes date, working hours, overtime, total time worked, and attendance status.
 * Typically used in reporting or dashboard views for managers.
 */
@Getter
@Setter
@Data
public class EmployeeLogs {
    private LocalDate date;
    private String startTime;
    private String endTime;
    private String regularHours;
    private String overtime;
    private String total;
    private AttendanceStatus status;
}
