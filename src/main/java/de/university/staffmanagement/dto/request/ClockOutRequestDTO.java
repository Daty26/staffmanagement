package de.university.staffmanagement.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClockOutRequestDTO {
    private LocalDateTime clockOutTime;
    private Long userId;
}
