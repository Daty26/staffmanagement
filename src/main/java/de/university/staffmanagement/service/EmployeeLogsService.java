package de.university.staffmanagement.service;


import de.university.staffmanagement.dto.request.EmployeeRequestDTO;
import de.university.staffmanagement.dto.response.EmployeeResponseDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for retrieving employee log summaries and weekly overviews.
 *
 * <p>This service is designed to support manager-facing views that display
 * an employee's attendance, working hours, and shift information for a given week.
 */
public interface EmployeeLogsService {


    /**
     * Retrieves a weekly overview of an employee's work logs starting from the specified week.
     *
     * @param userId the ID of the employee
     * @param weekStart the start date of the week (typically a Monday)
     * @return a list of {@link EmployeeResponseDTO} entries representing the employee's daily logs for that week
     */
    List<EmployeeResponseDTO> getWeeklyOverview(Long userId, LocalDate weekStart);
}
