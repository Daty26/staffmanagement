package de.university.staffmanagement.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClockResponseDTO {
    private LocalDateTime clockInTime;
    private LocalDateTime clockOutTime;
    private Long userId;
}
