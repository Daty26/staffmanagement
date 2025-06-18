package de.university.staffmanagement.dto.response;

import de.university.staffmanagement.enums.Role;
import de.university.staffmanagement.enums.ScheduleType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ShiftAssignmentResponse{
    private Long shiftId;
    private LocalDate shiftDate;
    //how many hours employee supposed to work
    private String startTime;
    private String endTime;
    private Role role;
    private ScheduleType shiftType;
    private String fullName;
}