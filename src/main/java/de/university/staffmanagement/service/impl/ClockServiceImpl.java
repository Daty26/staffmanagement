package de.university.staffmanagement.service.impl;

import de.university.staffmanagement.dto.request.ClockInRequestDTO;
import de.university.staffmanagement.dto.request.ClockOutRequestDTO;
import de.university.staffmanagement.dto.response.ClockResponseDTO;
import de.university.staffmanagement.entity.ClockEntry;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.exception.GeneralException;
import de.university.staffmanagement.mapper.ClockMapper;
import de.university.staffmanagement.repository.ClockEntryRepository;
import de.university.staffmanagement.repository.UserRepository;
import de.university.staffmanagement.service.ClockService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClockServiceImpl implements ClockService {
    private final ClockEntryRepository clockEntryRepository;
    private final ClockMapper clockMapper;
    private final UserRepository userRepository;
    public ClockServiceImpl(ClockEntryRepository clockEntryRepository, ClockMapper clockMapper, UserRepository userRepository) {
        this.clockEntryRepository = clockEntryRepository;
        this.clockMapper = clockMapper;
        this.userRepository = userRepository;
    }
    @Override
    public ClockResponseDTO clockIn(ClockInRequestDTO clockInRequestDTO) {

        boolean hasClockIn = clockEntryRepository.findClockoutNull().isPresent();
        if (hasClockIn) {
            throw new GeneralException("you have already clocked in");
        }
        User user = userRepository.findById(clockInRequestDTO.getUserId())
                .orElseThrow(() -> new GeneralException("User not found"));
        ClockEntry clockEntry = clockMapper.toEntity(clockInRequestDTO);
        clockEntry.setUser(user);
        clockEntryRepository.save(clockEntry);
        return clockMapper.toDTO(clockEntry);
    }
    @Override
    public ClockResponseDTO clockOut(ClockOutRequestDTO clockOutRequestDTO) {
        User user = userRepository.findById(clockOutRequestDTO.getUserId())
                .orElseThrow(() -> new GeneralException("User not found"));
        ClockEntry clockEntry = clockEntryRepository.findClockoutNull()
               .orElseThrow(() -> new GeneralException("you have not clocked in yet"));
        clockEntry.setUser(user);
       clockEntry.setClockOutTime(clockOutRequestDTO.getClockOutTime());
       clockEntryRepository.save(clockEntry);
       return clockMapper.toDTO(clockEntry);
    }

    @Override
    public List<ClockResponseDTO> getEntryByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException("User with this id was not found "));
        List<ClockEntry> clockEntries = clockEntryRepository.findByUser(user);
        return clockEntries.stream()
                .map(clockMapper::toDTO)
                .collect(Collectors.toList());

    }

    @Override
    public List<ClockResponseDTO> getAll() {
        return clockEntryRepository.findAll()
                .stream()
                .map(clockMapper::toDTO)
                .collect(Collectors.toList());
    }
}
