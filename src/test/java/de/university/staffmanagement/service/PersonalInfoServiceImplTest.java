package de.university.staffmanagement.service;

import de.university.staffmanagement.dto.request.PersonalInfoRequestDTO;
import de.university.staffmanagement.dto.response.PersonalInfoResponseDTO;
import de.university.staffmanagement.entity.PersonalInfo;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.exception.GeneralException;
import de.university.staffmanagement.mapper.PersonalInfoMapper;
import de.university.staffmanagement.repository.PersonalInfoRepository;
import de.university.staffmanagement.repository.UserRepository;
import de.university.staffmanagement.service.impl.PersonalInfoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonalInfoServiceImplTest {

    @Mock
    private PersonalInfoRepository personalInfoRepository;

    @Mock
    private PersonalInfoMapper personalInfoMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PersonalInfoServiceImpl personalInfoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void update_shouldCreateNewPersonalInfo_ifNotExists() {
        User user = new User();
        user.setUserId(1L);

        PersonalInfoRequestDTO request = new PersonalInfoRequestDTO();
        request.setFullName("Alice");
        request.setPhoneNumber("123456");
        request.setAddress("Test Street");
        request.setBirthDate(LocalDate.of(2000, 1, 1));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(personalInfoRepository.findByUser(user)).thenReturn(Optional.empty());

        PersonalInfo newInfo = new PersonalInfo();
        newInfo.setUser(user);
        when(personalInfoRepository.save(any())).thenReturn(newInfo);
        when(personalInfoMapper.toDTO(any())).thenReturn(new PersonalInfoResponseDTO(user));

        PersonalInfoResponseDTO result = personalInfoService.update(request, user);

        assertNotNull(result);
        verify(personalInfoRepository).save(any());
    }

    @Test
    void update_shouldThrow_whenUserNotFound() {
        User fakeUser = new User();
        fakeUser.setUserId(404L);

        when(userRepository.findById(404L)).thenReturn(Optional.empty());

        PersonalInfoRequestDTO request = new PersonalInfoRequestDTO();

        assertThrows(RuntimeException.class, () -> {
            personalInfoService.update(request, fakeUser);
        });
    }

    @Test
    void get_shouldReturnMappedDTO_ifExists() {
        User user = new User();
        user.setUserId(2L);

        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setUser(user);

        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(personalInfoRepository.findByUser(user)).thenReturn(Optional.of(personalInfo));

        PersonalInfoResponseDTO result = personalInfoService.get(user);

        assertNotNull(result);
    }

    @Test
    void get_shouldReturnDefaultDTO_ifNoInfoExists() {
        User user = new User();
        user.setUserId(3L);

        when(userRepository.findById(3L)).thenReturn(Optional.of(user));
        when(personalInfoRepository.findByUser(user)).thenReturn(Optional.empty());

        PersonalInfoResponseDTO result = personalInfoService.get(user);

    }

    @Test
    void get_shouldThrow_whenUserNotFound() {
        User user = new User();
        user.setUserId(100L);

        when(userRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(GeneralException.class, () -> personalInfoService.get(user));
    }

    @Test
    void getAll_shouldReturnListOfDTOs() {
        PersonalInfo info1 = new PersonalInfo();
        PersonalInfo info2 = new PersonalInfo();

        when(personalInfoRepository.findAll()).thenReturn(List.of(info1, info2));

        List<PersonalInfoResponseDTO> result = personalInfoService.getAll();

        assertEquals(2, result.size());
        verify(personalInfoMapper, times(2)).toDTO(any());
    }
}
