package de.university.staffmanagement.dto.response;

import de.university.staffmanagement.enums.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveResponseDTO {
    private String leaveType;
    private String startDate;
    private String endDate;
    private String reason;
    private Status status;
}
