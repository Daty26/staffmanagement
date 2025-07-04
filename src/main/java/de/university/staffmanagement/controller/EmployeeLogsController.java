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

@RequestMapping("/api/v1/logs")
@RestController
@Tag(name = "Controller for viewing employee logs")
public class EmployeeLogsController {

    private final EmployeeLogsService employeeLogsService;

    public EmployeeLogsController(EmployeeLogsService employeeLogsService) {
        this.employeeLogsService = employeeLogsService;
    }

    @GetMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<EmployeeResponseDTO>>> getWeeklyOverview(

            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate weekStart) {

        return  new ResponseEntity<>(new ResponseWrapper<>( employeeLogsService.getWeeklyOverview(userId, weekStart)), HttpStatus.OK);
//        return ResponseEntity.ok(overview);
    }
}
