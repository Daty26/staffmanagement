package de.university.staffmanagement.controller;

import de.university.staffmanagement.dto.request.ClockInRequestDTO;
import de.university.staffmanagement.dto.request.ClockOutRequestDTO;
import de.university.staffmanagement.dto.response.ClockResponseDTO;
import de.university.staffmanagement.dto.response.LeaveResponseDTO;
import de.university.staffmanagement.service.ClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clock")
public class ClockController {
    private final ClockService clockService;
    @Autowired
    public ClockController(ClockService clockService) {
        this.clockService = clockService;
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ClockResponseDTO>> getClockEntriesByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(clockService.getEntryByUserId(userId), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<ClockResponseDTO>> getAllClockEntries() {
        return new ResponseEntity<>(clockService.getAll(), HttpStatus.OK);
    }
    @PostMapping("/in")
    public ResponseEntity<ClockResponseDTO> clockIn(@RequestBody ClockInRequestDTO clockInRequestDTO) {
        return new ResponseEntity<>(clockService.clockIn(clockInRequestDTO), HttpStatus.CREATED);
    }


    @PostMapping("/out")
    public ResponseEntity<ClockResponseDTO> clockOut(@RequestBody ClockOutRequestDTO clockOutRequestDTO) {
        return new ResponseEntity<>(clockService.clockOut(clockOutRequestDTO), HttpStatus.OK);
    }

}
