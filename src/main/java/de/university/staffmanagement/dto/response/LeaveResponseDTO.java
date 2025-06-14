package de.university.staffmanagement.dto.response;

import de.university.staffmanagement.enums.LeaveType;
import de.university.staffmanagement.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveResponseDTO {
    private Long requestId;
    private LeaveType leaveType;
    private String startDate;
    private String endDate;
    private String reason;
    private Status status;
    @NotNull
    private String fullname;
}
