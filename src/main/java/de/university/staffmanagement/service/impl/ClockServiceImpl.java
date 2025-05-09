package de.university.staffmanagement.service.impl;

import de.university.staffmanagement.dto.request.ClockInRequestDTO;
import de.university.staffmanagement.dto.request.ClockOutRequestDTO;
import de.university.staffmanagement.dto.response.ClockResponseDTO;
import de.university.staffmanagement.entity.ClockEntry;
import de.university.staffmanagement.exception.GeneralException;
import de.university.staffmanagement.mapper.ClockMapper;
import de.university.staffmanagement.repository.ClockEntryRepository;
import de.university.staffmanagement.service.ClockService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ClockServiceImpl implements ClockService {
    private final ClockEntryRepository clockEntryRepository;
    private final ClockMapper clockMapper;
    public ClockServiceImpl(ClockEntryRepository clockEntryRepository, ClockMapper clockMapper) {
        this.clockEntryRepository = clockEntryRepository;
        this.clockMapper = clockMapper;
    }
    @Override
    public ClockResponseDTO clockIn(ClockInRequestDTO clockInRequestDTO) {

        boolean hasClockIn = clockEntryRepository.findClockoutNull().isPresent();
        if (hasClockIn) {
            throw new GeneralException("you have already clocked in");
        }
        ClockEntry clockEntry = clockMapper.toEntity(clockInRequestDTO);
        clockEntryRepository.save(clockEntry);
        return clockMapper.toDTO(clockEntry);
    }

    @Override
    public ClockResponseDTO clockOut(ClockOutRequestDTO clockOutRequestDTO) {
       ClockEntry clockEntry = clockEntryRepository.findClockoutNull().orElseThrow(() -> new GeneralException("you have not clocked in yet"));
       clockEntry.setClockOutTime(clockOutRequestDTO.getClockOutTime());
       clockEntryRepository.save(clockEntry);
       return clockMapper.toDTO(clockEntry);
    }
}
