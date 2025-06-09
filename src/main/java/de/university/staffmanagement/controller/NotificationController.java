package de.university.staffmanagement.controller;


import de.university.staffmanagement.dto.request.NotificationRequestDTO;
import de.university.staffmanagement.dto.response.NotificationResponseDTO;
import de.university.staffmanagement.dto.response.ResponseWrapper;
import de.university.staffmanagement.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<NotificationResponseDTO>> sendNotification(@RequestBody NotificationRequestDTO notificationRequestDTO) {
        return new ResponseEntity<>(new ResponseWrapper<>(notificationService.sendNotification(notificationRequestDTO)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
//        return ResponseEntity.noContent().build();
        return ResponseEntity.ok(new ResponseWrapper<>(null, ""));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseWrapper<List<NotificationResponseDTO>>> getNotificationsByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(new ResponseWrapper<>( notificationService.getNotificationByUserId(userId)), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<ResponseWrapper<List<NotificationResponseDTO>>> getAllNotifications() {
        List<NotificationResponseDTO> notifications = notificationService.getAll();
        return new ResponseEntity<>(new ResponseWrapper<>(notifications), HttpStatus.OK);
    }
}
