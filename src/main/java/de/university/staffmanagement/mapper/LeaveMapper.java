package de.university.staffmanagement.mapper;

import de.university.staffmanagement.dto.request.LeaveRequestDTO;
import de.university.staffmanagement.dto.response.LeaveResponseDTO;
import de.university.staffmanagement.entity.LeaveRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LeaveMapper {
    LeaveResponseDTO toDTO(LeaveRequest leaveRequest);
    @Mapping(source = "leaveType", target = "leaveType")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "reason", target = "reason")

    LeaveRequest toEntity(LeaveRequestDTO leaveRequestDTO);
}
