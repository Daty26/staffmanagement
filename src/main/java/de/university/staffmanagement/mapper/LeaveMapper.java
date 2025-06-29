package de.university.staffmanagement.mapper;

import de.university.staffmanagement.dto.request.LeaveRequestDTO;
import de.university.staffmanagement.dto.response.LeaveResponseDTO;
import de.university.staffmanagement.entity.LeaveRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LeaveMapper {
//    @Mapping(source = "userId", target = "user.userId")
    LeaveRequest toEntity(LeaveRequestDTO leaveRequestDTO);
//    @Mapping(source = "requestId", target = "requestId")
//    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.username", target = "userName")
//    @Mapping(source = "managerComment", target = "managerComment")
    LeaveResponseDTO toDTO(LeaveRequest leaveRequest);
}
