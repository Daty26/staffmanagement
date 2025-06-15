package de.university.staffmanagement.dto.request;

import de.university.staffmanagement.enums.ScheduleType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ShiftAssignmentRequest {
    private Long userId;
    private LocalDate shiftDate;
    private String startTime;
    private String endTime;
    private ScheduleType shiftType;
}
