package de.university.staffmanagement.controller;

import de.university.staffmanagement.dto.request.ClockInRequestDTO;
import de.university.staffmanagement.dto.request.ClockOutRequestDTO;
import de.university.staffmanagement.dto.response.ClockResponseDTO;
import de.university.staffmanagement.dto.response.LeaveResponseDTO;
import de.university.staffmanagement.dto.response.ResponseWrapper;
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
    public ResponseEntity<ResponseWrapper<List<ClockResponseDTO>>> getClockEntriesByUserId(@PathVariable Long userId) {
        List<ClockResponseDTO> entries =  clockService.getEntryByUserId(userId);
        return new ResponseEntity<>(new ResponseWrapper<>(entries), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ClockResponseDTO>>> getAllClockEntries() {
        return new ResponseEntity<>(new ResponseWrapper<>(clockService.getAll()), HttpStatus.OK);
    }
    @PostMapping("/in")
    public ResponseEntity<ResponseWrapper<ClockResponseDTO>> clockIn(@RequestBody ClockInRequestDTO clockInRequestDTO) {
        return new ResponseEntity<>(new ResponseWrapper<>(clockService.clockIn(clockInRequestDTO)), HttpStatus.CREATED);
    }


    @PostMapping("/out")
    public ResponseEntity<ResponseWrapper<ClockResponseDTO>> clockOut(@RequestBody ClockOutRequestDTO clockOutRequestDTO) {
        return new ResponseEntity<>(new ResponseWrapper<>(clockService.clockOut(clockOutRequestDTO)), HttpStatus.OK);
    }
}
