package de.university.staffmanagement.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeRequestDTO {
    private Long userId;
    private LocalDate weekStart;

}
