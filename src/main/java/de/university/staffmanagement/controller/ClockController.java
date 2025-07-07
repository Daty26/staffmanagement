package de.university.staffmanagement.controller;

import de.university.staffmanagement.dto.request.ClockInRequestDTO;
import de.university.staffmanagement.dto.request.ClockOutRequestDTO;
import de.university.staffmanagement.dto.response.ClockResponseDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import de.university.staffmanagement.dto.response.ResponseWrapper;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.service.ClockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST controller for handling clock-in and clock-out operations.
 *
 * <p>Provides endpoints for employees to record their working hours
 * and for managers to review all clock entries.
 */
@RestController
@RequestMapping("/api/v1/clock")
@Tag(name = "Controller for managing clock-in / clock-out")

public class ClockController {

    private final ClockService clockService;

    /**
     * Constructs the controller with injected clock service.
     *
     * @param clockService the service handling clock logic
     */
    @Autowired
    public ClockController(ClockService clockService) {
        this.clockService = clockService;
    }

    /**
     * Retrieves all clock-in/out entries for the currently authenticated user.
     *
     * @param user the authenticated user
     * @return a list of clock entries wrapped in a response object
     */
    @Operation(
            summary = "Get clock entries for current user",
            description = "Returns a list of clock-in/out entries for the authenticated user")
    @GetMapping("/user")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<ResponseWrapper<List<ClockResponseDTO>>> getClockEntriesByUserId(@AuthenticationPrincipal User user) {
        List<ClockResponseDTO> entries =  clockService.getEntryByUser(user);
        return new ResponseEntity<>(new ResponseWrapper<>(entries), HttpStatus.OK);
    }

    /**
     * Retrieves all clock entries in the system.
     * Accessible only by users with MANAGER role.
     *
     * @return a list of all clock entries
     */
    @Operation(
            summary = "Get all clock entries",
            description = "Returns a list of all clock-in/out entries in the system"
    )
    @GetMapping
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<ClockResponseDTO>>> getAllClockEntries() {
        return new ResponseEntity<>(new ResponseWrapper<>(clockService.getAll()), HttpStatus.OK);
    }

    /**
     * Clocks in the authenticated user.
     *
     * @param clockInRequestDTO contains clock-in time and metadata
     * @param user              the authenticated user
     * @return the clock-in response wrapped in a response object
     */
    @Operation(summary = "Clock in",
            description = "Allows authenticated user to clock in")
    @PostMapping("/in")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<ResponseWrapper<ClockResponseDTO>> clockIn(@RequestBody ClockInRequestDTO clockInRequestDTO, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(new ResponseWrapper<>(clockService.clockIn(clockInRequestDTO,  user)), HttpStatus.CREATED);
    }

    /**
     * Clocks out the authenticated user.
     *
     * @param clockOutRequestDTO contains clock-out time and metadata
     * @param user               the authenticated user
     * @return the clock-out response wrapped in a response object
     */
    @Operation(
            summary = "Clock out",
            description = "Allows authenticated user to clock out"
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping("/out")
    public ResponseEntity<ResponseWrapper<ClockResponseDTO>>  clockIn(@RequestBody ClockOutRequestDTO clockOutRequestDTO, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(new ResponseWrapper<>(clockService.clockOut(clockOutRequestDTO, user)), HttpStatus.OK);
    }
}
