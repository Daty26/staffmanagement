package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.NotificationRequestDTO;
import de.university.staffmanagement.dto.response.LeaveResponseDTO;
import de.university.staffmanagement.dto.response.NotificationResponseDTO;

import java.util.List;

public interface NotificationService {
    NotificationResponseDTO sendNotification(NotificationRequestDTO notificationRequestDTO);
    void deleteNotification(Long id);
    List<NotificationResponseDTO> getAll();
    List<NotificationResponseDTO> getNotificationByUserId(Long userId);
}
