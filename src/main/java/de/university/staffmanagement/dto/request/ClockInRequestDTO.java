package de.university.staffmanagement.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for submitting a clock-in request.
 *
 * <p>Contains the clock-in timestamp for the authenticated user.
 */
@Data
public class ClockInRequestDTO {
    private LocalDateTime clockInTime;
//    private Long userId;
}
