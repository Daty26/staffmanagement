package de.university.staffmanagement.controller;

import de.university.staffmanagement.dto.response.EmployeeResponseDTO;
import de.university.staffmanagement.dto.response.ResponseWrapper;
import de.university.staffmanagement.service.EmployeeLogsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;


import java.time.LocalDate;
import java.util.List;


/**
 * REST controller for retrieving employee attendance logs.
 *
 * <p>Provides an endpoint for managers to view an employee's clock-in/out and attendance
 * overview for a selected week.
 */
@RequestMapping("/api/v1/logs")
@RestController
@Tag(name = "Controller for viewing employee logs")
public class EmployeeLogsController {

    private final EmployeeLogsService employeeLogsService;

    /**
     * Constructs the controller with required service.
     *
     * @param employeeLogsService the service for fetching employee logs
     */
    public EmployeeLogsController(EmployeeLogsService employeeLogsService) {
        this.employeeLogsService = employeeLogsService;
    }

    /**
     * Returns a weekly overview of a given employee's attendance.
     * Only accessible to users with the MANAGER role.
     *
     * @param userId     the ID of the employee
     * @param weekStart  the start date (Monday) of the week to retrieve
     * @return a wrapped list of employee log responses for the given week
     */
    @GetMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<EmployeeResponseDTO>>> getWeeklyOverview(

            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate weekStart) {

        return  new ResponseEntity<>(new ResponseWrapper<>( employeeLogsService.getWeeklyOverview(userId, weekStart)), HttpStatus.OK);
//        return ResponseEntity.ok(overview);
    }
}
