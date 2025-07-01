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
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private Status status;
    private String managerComment;
    @NotNull
    private String userName;
}
