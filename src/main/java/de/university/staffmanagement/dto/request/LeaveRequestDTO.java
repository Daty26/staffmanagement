package de.university.staffmanagement.dto.request;

import de.university.staffmanagement.enums.LeaveType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO used for submitting a new leave request.
 *
 * <p>Includes leave type, date range, and an optional reason for the request.
 */
@Data
public class LeaveRequestDTO {
    private LeaveType leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
}
