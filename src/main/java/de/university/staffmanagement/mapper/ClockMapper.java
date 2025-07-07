package de.university.staffmanagement.mapper;
import de.university.staffmanagement.dto.request.ClockInRequestDTO;
import de.university.staffmanagement.dto.response.ClockResponseDTO;
import de.university.staffmanagement.entity.ClockEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting between {@link ClockEntry} entities and their corresponding DTOs.
 *
 * <p>This mapper uses MapStruct to generate implementation code at compile time.
 */
@Mapper(componentModel = "spring")
public interface ClockMapper {
    /**
     * Converts a {@link ClockInRequestDTO} to a {@link ClockEntry} entity.
     *
     * @param clockInRequestDTO the DTO containing clock-in time
     * @return the mapped ClockEntry entity
     */
    ClockEntry toEntity(ClockInRequestDTO clockInRequestDTO);
    /**
     * Converts a {@link ClockEntry} entity to a {@link ClockResponseDTO}.
     *
     * @param clockEntry the entity containing clock-in/out data
     * @return the mapped ClockResponseDTO with userId extracted from the user entity
     */
    @Mapping(source = "user.userId", target = "userId")
    ClockResponseDTO toDTO(ClockEntry clockEntry);
}
