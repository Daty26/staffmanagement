package de.university.staffmanagement.mapper;
import de.university.staffmanagement.dto.request.ClockInRequestDTO;
import de.university.staffmanagement.dto.response.ClockResponseDTO;
import de.university.staffmanagement.entity.ClockEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClockMapper {
    @Mapping(source = "userId", target = "user.userId")
    ClockEntry toEntity(ClockInRequestDTO clockInRequestDTO);
    @Mapping(source = "user.userId", target = "userId")
    ClockResponseDTO toDTO(ClockEntry clockEntry);
}
