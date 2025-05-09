package de.university.staffmanagement.mapper;

import de.university.staffmanagement.dto.request.ClockInRequestDTO;
import de.university.staffmanagement.dto.response.ClockResponseDTO;
import de.university.staffmanagement.entity.ClockEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClockMapper {
    ClockEntry toEntity(ClockInRequestDTO clockInRequestDTO);
    ClockResponseDTO toDTO(ClockEntry clockEntry);
}
