package de.university.staffmanagement.controller;

import de.university.staffmanagement.dto.request.ClockInRequestDTO;
import de.university.staffmanagement.dto.request.ClockOutRequestDTO;
import de.university.staffmanagement.dto.response.ClockResponseDTO;
import de.university.staffmanagement.dto.response.LeaveResponseDTO;
import de.university.staffmanagement.dto.response.ResponseWrapper;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.service.ClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @GetMapping("/user")
    public ResponseEntity<ResponseWrapper<List<ClockResponseDTO>>> getClockEntriesByUserId(@AuthenticationPrincipal User user) {
        List<ClockResponseDTO> entries =  clockService.getEntryByUser(user);
        return new ResponseEntity<>(new ResponseWrapper<>(entries), HttpStatus.OK);
    }
    //do we need this method?
    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ClockResponseDTO>>> getAllClockEntries() {
        return new ResponseEntity<>(new ResponseWrapper<>(clockService.getAll()), HttpStatus.OK);
    }
    @PostMapping("/in")
    public ResponseEntity<ResponseWrapper<ClockResponseDTO>> clockIn(@RequestBody ClockInRequestDTO clockInRequestDTO, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(new ResponseWrapper<>(clockService.clockIn(clockInRequestDTO,  user)), HttpStatus.CREATED);
    }


    @PostMapping("/out")
    public ResponseEntity<ResponseWrapper<ClockResponseDTO>>  clockIn(@RequestBody ClockOutRequestDTO clockOutRequestDTO, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(new ResponseWrapper<>(clockService.clockOut(clockOutRequestDTO, user)), HttpStatus.OK);
    }
}
