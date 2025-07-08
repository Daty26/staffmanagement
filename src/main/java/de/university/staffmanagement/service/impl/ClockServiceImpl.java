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

/**
 * Handles clock-in and clock-out operations and provides clock entry data.
 */
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
    /**
     * Records a clock-in time for the authenticated user.
     *
     * <p>Ensures the user is not already clocked in.
     *
     * @param clockInRequestDTO DTO containing the clock-in time
     * @param authenticatedUser the user who is clocking in
     * @return a {@link ClockResponseDTO} containing the recorded clock-in entry
     * @throws GeneralException if the user is already clocked in
     */
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

    /**
     * Records a clock-out time for the authenticated user.
     *
     * <p>Validates that the user has a pending clock-in and has worked at least one hour.
     *
     * @param clockOutRequestDTO DTO containing the clock-out time
     * @param authenticatedUser the user who is clocking out
     * @return a {@link ClockResponseDTO} with the completed clock-in/out entry
     * @throws GeneralException if the user hasn't clocked in or has worked less than an hour
     */
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
    /**
     * Retrieves all clock entries for a specific user.
     *
     * @param user the user whose entries to retrieve
     * @return a list of {@link ClockResponseDTO} objects
     */
    @Override
    public List<ClockResponseDTO> getEntryByUser(User user) {
        List<ClockEntry> clockEntries = clockEntryRepository.findByUser_UserId(user.getUserId());
        return clockEntries.stream()
                .map(clockMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all clock entries in the system.
     *
     * @return a list of all {@link ClockResponseDTO} objects
     */
    @Override
    public List<ClockResponseDTO> getAll() {
        return clockEntryRepository.findAll()
                .stream()
                .map(clockMapper::toDTO)
                .collect(Collectors.toList());
    }
}
