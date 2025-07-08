package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.NotificationRequestDTO;
import de.university.staffmanagement.dto.response.NotificationResponseDTO;
import de.university.staffmanagement.entity.Notification;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.exception.GeneralException;
import de.university.staffmanagement.mapper.NotificationMapper;
import de.university.staffmanagement.repository.NotificationRepository;
import de.university.staffmanagement.repository.UserRepository;
import de.university.staffmanagement.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationMapper notificationMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendNotification_withDTO_shouldSaveNotification() {
        NotificationRequestDTO dto = new NotificationRequestDTO();
        dto.setUsername("john");
        dto.setMessage("Test message");

        User user = new User();
        user.setUsername("john");

        Notification notification = new Notification();
        notification.setMessage("Test message");
        notification.setUser(user);
        notification.setSentDate(LocalDateTime.now());

        NotificationResponseDTO responseDTO = new NotificationResponseDTO();

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(notificationMapper.toDTO(any())).thenReturn(responseDTO);

        NotificationResponseDTO result = notificationService.sendNotification(dto);

        assertNotNull(result);
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    void sendNotification_withDTO_shouldThrowIfUserNotFound() {
        NotificationRequestDTO dto = new NotificationRequestDTO();
        dto.setUsername("unknown");

        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> notificationService.sendNotification(dto));
    }

    @Test
    void sendNotification_withUsernameAndMessage_shouldSaveNotification() {
        User user = new User();
        user.setUsername("emma");

        when(userRepository.findByUsername("emma")).thenReturn(Optional.of(user));

        notificationService.sendNotification("emma", "Hello!");

        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    void sendNotification_withUsername_shouldThrowIfUserNotFound() {
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> notificationService.sendNotification("ghost", "Hi"));
    }

    @Test
    void deleteNotification_shouldCallRepository() {
        notificationService.deleteNotification(1L);
        verify(notificationRepository).deleteById(1L);
    }

    @Test
    void getAll_shouldReturnMappedList() {
        Notification n1 = new Notification();
        Notification n2 = new Notification();

        when(notificationRepository.findAll()).thenReturn(List.of(n1, n2));
        when(notificationMapper.toDTO(any())).thenReturn(new NotificationResponseDTO());

        List<NotificationResponseDTO> result = notificationService.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void getNotificationByUserId_shouldReturnUserNotifications() {
        User user = new User();
        user.setUserId(1L);

        Notification n = new Notification();
        n.setUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(notificationRepository.findByUser(user)).thenReturn(List.of(n));
        when(notificationMapper.toDTO(n)).thenReturn(new NotificationResponseDTO());

        List<NotificationResponseDTO> result = notificationService.getNotificationByUserId(1L);

        assertEquals(1, result.size());
    }

    @Test
    void getNotificationByUserId_shouldThrowIfUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(GeneralException.class, () -> notificationService.getNotificationByUserId(999L));
    }
}
