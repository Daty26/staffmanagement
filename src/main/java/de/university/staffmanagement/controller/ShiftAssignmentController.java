package de.university.staffmanagement.controller;

import de.university.staffmanagement.dto.request.ShiftAssignmentRequest;
import de.university.staffmanagement.dto.response.PersonalInfoResponseDTO;
import de.university.staffmanagement.dto.response.ResponseWrapper;
import de.university.staffmanagement.dto.response.ShiftAssignmentResponse;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.service.ShiftAssignmentService;
import de.university.staffmanagement.service.impl.ShiftAssignmentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shift")
@SecurityRequirement(name = "JWT")
@Tag(name = "Controller for managing shift assignments")
public class ShiftAssignmentController {
    ShiftAssignmentService shiftAssignmentService;

    public ShiftAssignmentController(ShiftAssignmentService shiftAssignmentService) {
        this.shiftAssignmentService = shiftAssignmentService;
    }
    @Operation(
            summary = "Create shift assignment",
            description = "Create a new shift assignment for a user"
    )
    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE')")
    public ResponseEntity<ResponseWrapper<ShiftAssignmentResponse>> create(@RequestBody ShiftAssignmentRequest request) {
        return new ResponseEntity<>(new ResponseWrapper<>(shiftAssignmentService.create(request)), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all shift assignments",
            description = "Retrieve all shift assignments in the system"
    )
    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE')")
    public ResponseEntity<ResponseWrapper<List<ShiftAssignmentResponse>>> getAll() {
        return new ResponseEntity<>(new ResponseWrapper<>(shiftAssignmentService.getAll()), HttpStatus.OK);
    }

    @Operation(
            summary = "Get user shift assignments",
            description = "Retrieve shift assignments for the authenticated user"
    )
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE')")
    public ResponseEntity<ResponseWrapper<List<ShiftAssignmentResponse>>> getByUser(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(new ResponseWrapper<>(shiftAssignmentService.getByUserId(user.getUserId())), HttpStatus.OK);
    }
}
