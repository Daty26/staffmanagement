package de.university.staffmanagement.dto.request;

import de.university.staffmanagement.enums.LeaveType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LeaveRequestDTO {
    private LeaveType leaveType;
    private String startDate;
    private String endDate;
    private String reason;
//    private Long userId;
}
