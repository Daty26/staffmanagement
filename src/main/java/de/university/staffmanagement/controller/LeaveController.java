package de.university.staffmanagement.controller;

import de.university.staffmanagement.dto.request.LeaveRequestDTO;
import de.university.staffmanagement.dto.request.UserRequestDTO;
import de.university.staffmanagement.dto.response.LeaveResponseDTO;
import de.university.staffmanagement.dto.response.ResponseWrapper;
import de.university.staffmanagement.dto.response.UserResponseDTO;
import de.university.staffmanagement.enums.Status;
import de.university.staffmanagement.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:8087")
@RequestMapping("/api/v1/leaves")
public class LeaveController {
    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<LeaveResponseDTO>> createRequest(@RequestBody LeaveRequestDTO leaveRequestDTO) {
        LeaveResponseDTO response = leaveService.create(leaveRequestDTO);
//        System.out.println(response);
        return new ResponseEntity<>(new ResponseWrapper<>(response), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<LeaveResponseDTO>>> getAllRequests() {
        return new ResponseEntity<>(new ResponseWrapper<>(leaveService.getAll()), HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseWrapper<List<LeaveResponseDTO>>> getRequestsByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(new ResponseWrapper<>(leaveService.getReqByUserId(userId)), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<LeaveResponseDTO>> getRequestById(@PathVariable Long id) {
        return new ResponseEntity<>(new ResponseWrapper<>(leaveService.getReqById(id)), HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseWrapper<List<LeaveResponseDTO>>> getLeaveRequestsByStatus(@PathVariable Status status) {
        return new ResponseEntity<>(new ResponseWrapper<>(leaveService.getByStatus(status)), HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ResponseWrapper<LeaveResponseDTO>> updateStatus(@PathVariable Long id, @RequestParam Status status) {;
        return new ResponseEntity<>(new ResponseWrapper<>(leaveService.updateStatus(id, status)), HttpStatus.OK);
    }
}
