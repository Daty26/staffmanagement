package de.university.staffmanagement.dto.request;

import lombok.Data;

import java.time.LocalDate;

/**
 * DTO used for requesting employee-related data such as weekly logs or summaries.
 *
 * <p>Includes the user ID and the starting date of the week to query.
 */
@Data
public class EmployeeRequestDTO {
    private Long userId;
    private LocalDate weekStart;

}
