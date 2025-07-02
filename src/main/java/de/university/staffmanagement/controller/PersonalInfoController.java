package de.university.staffmanagement.controller;

import de.university.staffmanagement.dto.request.NotificationRequestDTO;
import de.university.staffmanagement.dto.request.PersonalInfoRequestDTO;
import de.university.staffmanagement.dto.response.NotificationResponseDTO;
import de.university.staffmanagement.dto.response.PersonalInfoResponseDTO;
import de.university.staffmanagement.dto.response.ResponseWrapper;
import de.university.staffmanagement.dto.response.UserResponseDTO;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.service.PersonalInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;


import java.util.List;

@RestController
@RequestMapping("/api/personalInfo")
@Tag(name = "Controller for managing personal information")

public class PersonalInfoController {

    PersonalInfoService personalInfoService;

    public PersonalInfoController(PersonalInfoService personalInfoService) {
        this.personalInfoService = personalInfoService;
    }
    @Operation(
            summary = "Update personal information",
            description = "Update personal information for the authenticated user")
    @SecurityRequirement(name = "JWT")
    @PutMapping("/updatePersonalInfo")
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE')")
    public ResponseEntity<ResponseWrapper<PersonalInfoResponseDTO>> updatePersonalInfo(@RequestBody PersonalInfoRequestDTO personalInfoRequestDTO,@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(new ResponseWrapper<>(personalInfoService.update(personalInfoRequestDTO, user)), HttpStatus.CREATED);
    }
    @Operation(
            summary = "Get personal information",
            description = "Retrieve personal information of the authenticated user"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<PersonalInfoResponseDTO>> getUserById(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(new ResponseWrapper<>(personalInfoService.get(user)), HttpStatus.OK);
    }
    @Operation(
            summary = "Get all personal information",
            description = "Retrieve personal information for all users"
    )
    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<PersonalInfoResponseDTO>>> getAllUsers() {
        return new ResponseEntity<>(new ResponseWrapper<>(personalInfoService.getAll()), HttpStatus.OK);
    }

}