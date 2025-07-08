package de.university.staffmanagement.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO representing a clock-in and clock-out record for a user.
 *
 * <p>Includes timestamps and the associated user's ID.
 */
@Data
public class ClockResponseDTO {
    private LocalDateTime clockInTime;
    private LocalDateTime clockOutTime;
    @NotNull
    private Long userId;
}
