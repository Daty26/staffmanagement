package de.university.staffmanagement.controller;

import de.university.staffmanagement.dto.request.EmployeeRequestDTO;
import de.university.staffmanagement.dto.response.EmployeeResponseDTO;
import de.university.staffmanagement.service.EmployeeLogsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.access.prepost.PreAuthorize;


import java.util.List;

@Controller
public class EmployeeLogsController {

    private final EmployeeLogsService employeeLogsService;

    public EmployeeLogsController(EmployeeLogsService employeeLogsService) {
        this.employeeLogsService = employeeLogsService;
    }

    @GetMapping("/logs")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<EmployeeResponseDTO>> getWeeklyOverview(
            @RequestBody EmployeeRequestDTO request) {

        List<EmployeeResponseDTO> overview = employeeLogsService.getWeeklyOverview(request);
        return ResponseEntity.ok(overview);
    }
}
