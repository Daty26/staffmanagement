package de.university.staffmanagement.service.impl;


import de.university.staffmanagement.dto.request.NotificationRequestDTO;
import de.university.staffmanagement.dto.response.NotificationResponseDTO;
import de.university.staffmanagement.entity.Notification;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.mapper.NotificationMapper;
import de.university.staffmanagement.repository.NotificationRepository;
import de.university.staffmanagement.repository.UserRepository;
import de.university.staffmanagement.service.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository, NotificationMapper notificationMapper, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
        this.userRepository = userRepository;
    }

    @Override
    public NotificationResponseDTO sendNotification(NotificationRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Notification notification = new Notification();
        notification.setMessage(dto.getMessage());
        notification.setUser(user);
        notification.setSentDate(LocalDateTime.now());
        notification.setRead(false);

        notificationRepository.save(notification);
        return notificationMapper.toDTO(notification);
    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public List<NotificationResponseDTO> getAll() {
        return notificationRepository.findAll()
                .stream()
                .map(notificationMapper::toDTO)
                .collect(Collectors.toList());
    }
}
