package de.university.staffmanagement.mapper;

import de.university.staffmanagement.dto.request.ShiftAssignmentRequest;
import de.university.staffmanagement.dto.response.ShiftAssignmentResponse;
import de.university.staffmanagement.entity.ShiftAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface ShiftAssignmentMapper {
    @Mapping(source = "userId", target = "user.userId")
    @Mapping(target = "startTime", expression = "java(java.time.LocalTime.parse(dto.getStartTime()))")
    @Mapping(target = "endTime", expression = "java(java.time.LocalTime.parse(dto.getEndTime()))")
    ShiftAssignment toEntity(ShiftAssignmentRequest dto);

    @Mapping(source = "shift.shiftId", target = "shiftId")
    @Mapping(target = "startTime", expression = "java(shift.getStartTime().toString())")
    @Mapping(target = "endTime", expression = "java(shift.getEndTime().toString())")
    @Mapping(target = "fullName", expression = "java(fullName)")
    @Mapping(target = "role", expression = "java(shift.getUser().getRole())")
    ShiftAssignmentResponse toDTO(ShiftAssignment shift, String fullName);
}
