package de.university.staffmanagement.controller;

import de.university.staffmanagement.dto.request.NotificationRequestDTO;
import de.university.staffmanagement.dto.request.PersonalInfoRequestDTO;
import de.university.staffmanagement.dto.response.NotificationResponseDTO;
import de.university.staffmanagement.dto.response.PersonalInfoResponseDTO;
import de.university.staffmanagement.dto.response.UserResponseDTO;
import de.university.staffmanagement.service.PersonalInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personalInfo")
public class PersonalInfoController {

    PersonalInfoService personalInfoService;

    public PersonalInfoController(PersonalInfoService personalInfoService) {
        this.personalInfoService = personalInfoService;
    }
    @PutMapping("/updatePersonalInfo")
    public ResponseEntity<PersonalInfoResponseDTO> updatePersonalInfo(@RequestBody PersonalInfoRequestDTO personalInfoRequestDTO) {
        return new ResponseEntity<>(personalInfoService.update(personalInfoRequestDTO), HttpStatus.CREATED);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<PersonalInfoResponseDTO> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(personalInfoService.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PersonalInfoResponseDTO>> getAllUsers() {
        return new ResponseEntity<>(personalInfoService.getAll(), HttpStatus.OK);
    }
}
