package de.university.staffmanagement.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClockResponseDTO {
    private LocalDateTime clockInTime;
    private LocalDateTime clockOutTime;
    @NotNull
    private Long userId;
}
