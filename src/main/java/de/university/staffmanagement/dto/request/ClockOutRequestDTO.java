package de.university.staffmanagement.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO used for submitting a clock-out request.
 * <p>Contains the timestamp when the user ends their shift.
 */
@Data
public class ClockOutRequestDTO {
    private LocalDateTime clockOutTime;
//    private Long userId;
}
