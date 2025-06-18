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
    public ClockResponseDTO clockIn(ClockInRequestDTO clockInRequestDTO, User authenticatedUser) {

        boolean hasClockIn = clockEntryRepository.findByUser_UserIdAndClockOutTimeIsNull(authenticatedUser.getUserId()).isPresent();
        if (hasClockIn) {
            throw new GeneralException("you have already clocked in");
        }
        ClockEntry clockEntry = clockMapper.toEntity(clockInRequestDTO);
        clockEntry.setUser(authenticatedUser);
        clockEntryRepository.save(clockEntry);
        return clockMapper.toDTO(clockEntry);
    }

    @Override
    public ClockResponseDTO clockOut(ClockOutRequestDTO clockOutRequestDTO, User authenticatedUser) {
        //work on this method down bellow
        ClockEntry clockEntry = clockEntryRepository.findByUser_UserIdAndClockOutTimeIsNull(authenticatedUser.getUserId())
               .orElseThrow(() -> new GeneralException("you have not clocked in yet"));

        clockEntry.setUser(authenticatedUser);

        if (clockEntry.getClockInTime() != null && clockOutRequestDTO.getClockOutTime() != null) {
            long minutesWorked = java.time.Duration.between(clockEntry.getClockInTime(), clockOutRequestDTO.getClockOutTime()).toMinutes();
            if (minutesWorked < 60) {
                throw new GeneralException("You must work at least 1 hour before clocking out. You’ve worked " + minutesWorked + " minutes.");
            }
        }

        clockEntry.setClockOutTime(clockOutRequestDTO.getClockOutTime());
        clockEntryRepository.save(clockEntry);
        return clockMapper.toDTO(clockEntry);
    }

    @Override
    public List<ClockResponseDTO> getEntryByUser(User user) {
        List<ClockEntry> clockEntries = clockEntryRepository.findByUser_UserId(user.getUserId());
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
