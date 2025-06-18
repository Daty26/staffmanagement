package de.university.staffmanagement.controller;

import de.university.staffmanagement.dto.request.ShiftAssignmentRequest;
import de.university.staffmanagement.dto.response.PersonalInfoResponseDTO;
import de.university.staffmanagement.dto.response.ResponseWrapper;
import de.university.staffmanagement.dto.response.ShiftAssignmentResponse;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.service.ShiftAssignmentService;
import de.university.staffmanagement.service.impl.ShiftAssignmentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shift")
public class ShiftAssignmentController {
    ShiftAssignmentService shiftAssignmentService;

    public ShiftAssignmentController(ShiftAssignmentService shiftAssignmentService) {
        this.shiftAssignmentService = shiftAssignmentService;
    }
    @PostMapping
    public ResponseEntity<ResponseWrapper<ShiftAssignmentResponse>> create(@RequestBody ShiftAssignmentRequest request) {
        return new ResponseEntity<>(new ResponseWrapper<>(shiftAssignmentService.create(request)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ShiftAssignmentResponse>>> getAll() {
        return new ResponseEntity<>(new ResponseWrapper<>(shiftAssignmentService.getAll()), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<ResponseWrapper<List<ShiftAssignmentResponse>>> getByUser(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(new ResponseWrapper<>(shiftAssignmentService.getByUserId(user.getUserId())), HttpStatus.OK);
    }
}
