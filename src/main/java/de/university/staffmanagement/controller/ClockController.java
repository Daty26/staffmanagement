package de.university.staffmanagement.controller;

import de.university.staffmanagement.dto.request.ClockInRequestDTO;
import de.university.staffmanagement.dto.request.ClockOutRequestDTO;
import de.university.staffmanagement.dto.response.ClockResponseDTO;
import de.university.staffmanagement.service.ClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clock")
public class ClockController {
    private final ClockService clockService;
    @Autowired
    public ClockController(ClockService clockService) {
        this.clockService = clockService;
    }
    @PostMapping("/in")
    public ClockResponseDTO clockIn( @RequestBody ClockInRequestDTO clockInRequestDTO) {
        return clockService.clockIn(clockInRequestDTO);
    }
    @PostMapping("/out")
    public ClockResponseDTO clockOut(@RequestBody ClockOutRequestDTO clockOutRequestDTO) {
        return clockService.clockOut(clockOutRequestDTO);
    }
}
