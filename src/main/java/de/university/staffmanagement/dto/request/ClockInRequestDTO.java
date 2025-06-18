package de.university.staffmanagement.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClockInRequestDTO {
    private LocalDateTime clockInTime;
//    private Long userId;
}
