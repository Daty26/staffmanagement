package de.university.staffmanagement.controller;

import de.university.staffmanagement.dto.request.LeaveRequestDTO;
import de.university.staffmanagement.dto.request.LeaveStatusUpdateDTO;
import de.university.staffmanagement.dto.request.UserRequestDTO;
import de.university.staffmanagement.dto.response.LeaveResponseDTO;
import de.university.staffmanagement.dto.response.ResponseWrapper;
import de.university.staffmanagement.dto.response.UserResponseDTO;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.enums.Status;
import de.university.staffmanagement.service.LeaveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:8087")
@RequestMapping("/api/v1/leaves")
@Tag(name = "Controller for managing leave requests")
public class LeaveController {
    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }
    @Operation(
            summary = "Create leave request",
            description = "Submit a new leave request for the authenticated user"
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping
    public ResponseEntity<ResponseWrapper<LeaveResponseDTO>> createRequest(@RequestBody LeaveRequestDTO leaveRequestDTO, @AuthenticationPrincipal User user ) {
        LeaveResponseDTO response = leaveService.create(leaveRequestDTO, user);
//        System.out.println(response);
        return new ResponseEntity<>(new ResponseWrapper<>(response), HttpStatus.CREATED);
    }
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Get all leave requests",
            description = "Retrieve all leave requests in the system"
    )
    @GetMapping
    public ResponseEntity<ResponseWrapper<List<LeaveResponseDTO>>> getAllRequests() {
        return new ResponseEntity<>(new ResponseWrapper<>(leaveService.getAll()), HttpStatus.OK);
    }
    @Operation(
            summary = "Get leave requests of current user",
            description = "Retrieve leave requests submitted by the authenticated user"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/user")
    public ResponseEntity<ResponseWrapper<List<LeaveResponseDTO>>> getRequestsByUserId(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(new ResponseWrapper<>(leaveService.getReqByUserId(user.getUserId())), HttpStatus.OK);
    }

    @Operation(
            summary = "Get leave request by ID",
            description = "Retrieve a specific leave request by its ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<LeaveResponseDTO>> getRequestById(@PathVariable Long id) {
        return new ResponseEntity<>(new ResponseWrapper<>(leaveService.getReqById(id)), HttpStatus.OK);
    }

    @Operation(
            summary = "Get leave requests by status",
            description = "Retrieve leave requests based on their current status"
    )
    @SecurityRequirement(name = "JWT")
    @Parameter(
            name = "status",
            description = "Leave request status (e.g. PENDING, APPROVED, REJECTED)",
            required = true
    )
    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseWrapper<List<LeaveResponseDTO>>> getLeaveRequestsByStatus(@PathVariable Status status, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(new ResponseWrapper<>(leaveService.getByStatus(status, user.getUserId())), HttpStatus.OK);
    }

    @Operation(
            summary = "Update leave request status",
            description = "Approve or reject a leave request and add manager's comment"
    )
    @Parameter(
            name = "id",
            description = "Leave request ID",
            required = true
    )
    @PutMapping("/{id}/status")
    public ResponseEntity<ResponseWrapper<LeaveResponseDTO>> updateStatus(@PathVariable Long id, @RequestBody LeaveStatusUpdateDTO updateDTO) {;
        return new ResponseEntity<>(new ResponseWrapper<>(leaveService.updateStatus(id, updateDTO)), HttpStatus.OK);
    }
}
