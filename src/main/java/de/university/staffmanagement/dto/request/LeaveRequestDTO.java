package de.university.staffmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LeaveRequestDTO {
    @NotBlank(message = "Leave type can not be empty")
    private String leaveType;
    @NotBlank(message = "Start date can not be empty")
    private String startDate;
    @NotBlank(message = "End date can not be empty")
    private String endDate;
    @NotBlank(message = "Reason can not be empty")
    private String reason;
}
