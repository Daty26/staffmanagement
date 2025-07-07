package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.ClockInRequestDTO;
import de.university.staffmanagement.dto.request.ClockOutRequestDTO;
import de.university.staffmanagement.dto.response.ClockResponseDTO;
import de.university.staffmanagement.entity.ClockEntry;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.exception.GeneralException;
import de.university.staffmanagement.mapper.ClockMapper;
import de.university.staffmanagement.repository.ClockEntryRepository;
import de.university.staffmanagement.repository.UserRepository;
import de.university.staffmanagement.service.impl.ClockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClockServiceImplTest {

    @Mock
    private ClockEntryRepository clockEntryRepository;

    @Mock
    private ClockMapper clockMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ClockServiceImpl clockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void clockIn_shouldSaveEntry_whenNotAlreadyClockedIn() {
        User user = new User();
        user.setUserId(1L);

        ClockInRequestDTO requestDTO = new ClockInRequestDTO();

        ClockEntry clockEntry = new ClockEntry();
        clockEntry.setUser(user);

        when(clockEntryRepository.findByUser_UserIdAndClockOutTimeIsNull(1L)).thenReturn(Optional.empty());
        when(clockMapper.toEntity(requestDTO)).thenReturn(clockEntry);
        when(clockMapper.toDTO(clockEntry)).thenReturn(new ClockResponseDTO());

        ClockResponseDTO result = clockService.clockIn(requestDTO, user);

        assertNotNull(result);
        verify(clockEntryRepository).save(clockEntry);
    }

    @Test
    void clockIn_shouldThrow_whenAlreadyClockedIn() {
        User user = new User();
        user.setUserId(1L);

        when(clockEntryRepository.findByUser_UserIdAndClockOutTimeIsNull(1L))
                .thenReturn(Optional.of(new ClockEntry()));

        assertThrows(GeneralException.class, () -> clockService.clockIn(new ClockInRequestDTO(), user));
    }

    @Test
    void clockOut_shouldSave_whenClockedInAndWorkedEnough() {
        User user = new User();
        user.setUserId(2L);

        ClockEntry entry = new ClockEntry();
        entry.setClockInTime(LocalDateTime.now().minusHours(2));
        entry.setUser(user);

        ClockOutRequestDTO requestDTO = new ClockOutRequestDTO();
        requestDTO.setClockOutTime(LocalDateTime.now());

        when(clockEntryRepository.findByUser_UserIdAndClockOutTimeIsNull(2L)).thenReturn(Optional.of(entry));
        when(clockMapper.toDTO(entry)).thenReturn(new ClockResponseDTO());

        ClockResponseDTO result = clockService.clockOut(requestDTO, user);

        assertNotNull(result);
        verify(clockEntryRepository).save(entry);
    }

    @Test
    void clockOut_shouldThrow_whenNotClockedIn() {
        User user = new User();
        user.setUserId(3L);

        when(clockEntryRepository.findByUser_UserIdAndClockOutTimeIsNull(3L)).thenReturn(Optional.empty());

        assertThrows(GeneralException.class, () -> clockService.clockOut(new ClockOutRequestDTO(), user));
    }

    @Test
    void clockOut_shouldThrow_whenWorkedLessThan1Hour() {
        User user = new User();
        user.setUserId(4L);

        ClockEntry entry = new ClockEntry();
        entry.setUser(user);
        entry.setClockInTime(LocalDateTime.now().minusMinutes(30));

        ClockOutRequestDTO requestDTO = new ClockOutRequestDTO();
        requestDTO.setClockOutTime(LocalDateTime.now());

        when(clockEntryRepository.findByUser_UserIdAndClockOutTimeIsNull(4L)).thenReturn(Optional.of(entry));

        GeneralException ex = assertThrows(GeneralException.class, () -> clockService.clockOut(requestDTO, user));
        assertTrue(ex.getMessage().contains("You must work at least 1 hour"));
    }

    @Test
    void getEntryByUser_shouldReturnListOfDTOs() {
        User user = new User();
        user.setUserId(5L);

        ClockEntry entry = new ClockEntry();
        entry.setUser(user);

        when(clockEntryRepository.findByUser_UserId(5L)).thenReturn(List.of(entry));
        when(clockMapper.toDTO(entry)).thenReturn(new ClockResponseDTO());

        List<ClockResponseDTO> result = clockService.getEntryByUser(user);

        assertEquals(1, result.size());
    }

    @Test
    void getAll_shouldReturnAllClockEntries() {
        ClockEntry entry1 = new ClockEntry();
        ClockEntry entry2 = new ClockEntry();

        when(clockEntryRepository.findAll()).thenReturn(List.of(entry1, entry2));
        when(clockMapper.toDTO(any())).thenReturn(new ClockResponseDTO());

        List<ClockResponseDTO> result = clockService.getAll();

        assertEquals(2, result.size());
    }
}