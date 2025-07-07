package de.university.staffmanagement.mapper;

import de.university.staffmanagement.dto.request.ShiftAssignmentRequest;
import de.university.staffmanagement.dto.response.ShiftAssignmentResponse;
import de.university.staffmanagement.entity.ShiftAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

/**
 * Mapper interface for converting between {@link ShiftAssignment} entities and their corresponding DTOs.
 *
 * <p>Handles the transformation of time fields and associated user details to match frontend expectations.
 */
@Mapper(componentModel = "spring")
public interface ShiftAssignmentMapper {
    /**
     * Converts a {@link ShiftAssignmentRequest} DTO to a {@link ShiftAssignment} entity.
     *
     * <p>Parses start and end time strings into {@code LocalTime} objects and maps user ID.
     *
     * @param dto the request DTO containing shift details
     * @return the ShiftAssignment entity
     */
    @Mapping(source = "userId", target = "user.userId")
    @Mapping(target = "startTime", expression = "java(java.time.LocalTime.parse(dto.getStartTime()))")
    @Mapping(target = "endTime", expression = "java(java.time.LocalTime.parse(dto.getEndTime()))")
    ShiftAssignment toEntity(ShiftAssignmentRequest dto);

    /**
     * Converts a {@link ShiftAssignment} entity to a {@link ShiftAssignmentResponse} DTO.
     *
     * <p>Includes string representations of time fields and injects the user's full name and role.
     *
     * @param shift the shift assignment entity
     * @param fullName the full name of the associated user
     * @return the response DTO for frontend usage
     */
    @Mapping(source = "shift.shiftId", target = "shiftId")
    @Mapping(target = "startTime", expression = "java(shift.getStartTime().toString())")
    @Mapping(target = "endTime", expression = "java(shift.getEndTime().toString())")
    @Mapping(target = "fullName", expression = "java(fullName)")
    @Mapping(target = "role", expression = "java(shift.getUser().getRole())")
    ShiftAssignmentResponse toDTO(ShiftAssignment shift, String fullName);
}
