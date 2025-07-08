package de.university.staffmanagement.mapper;

import de.university.staffmanagement.dto.request.LeaveRequestDTO;
import de.university.staffmanagement.dto.response.LeaveResponseDTO;
import de.university.staffmanagement.entity.LeaveRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting between {@link LeaveRequest} entities and their corresponding DTOs.
 *
 * <p>Used to map leave request data between internal entity representation and external-facing DTOs.
 */
@Mapper(componentModel = "spring")
public interface LeaveMapper {
    /**
     * Converts a {@link LeaveRequestDTO} to a {@link LeaveRequest} entity.
     *
     * @param leaveRequestDTO the DTO containing leave request details
     * @return the mapped LeaveRequest entity
     */
    LeaveRequest toEntity(LeaveRequestDTO leaveRequestDTO);
    /**
     * Converts a {@link LeaveRequest} entity to a {@link LeaveResponseDTO}.
     *
     * <p>Includes user’s username as {@code userName} in the response DTO.
     *
     * @param leaveRequest the leave request entity
     * @return the mapped LeaveResponseDTO
     */
    @Mapping(source = "user.username", target = "userName")
    LeaveResponseDTO toDTO(LeaveRequest leaveRequest);
}
