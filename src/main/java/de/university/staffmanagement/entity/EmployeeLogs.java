package de.university.staffmanagement.entity;

import de.university.staffmanagement.enums.AttendanceStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
