package de.university.staffmanagement.mapper;

import de.university.staffmanagement.dto.request.LeaveRequestDTO;
import de.university.staffmanagement.dto.response.LeaveResponseDTO;
import de.university.staffmanagement.entity.LeaveRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LeaveMapper {
    LeaveResponseDTO toDTO(LeaveRequest leaveRequest);
    LeaveRequest toEntity(LeaveRequestDTO leaveRequestDTO);
}
