package de.university.staffmanagement.mapper;

import de.university.staffmanagement.dto.request.PersonalInfoRequestDTO;
import de.university.staffmanagement.dto.response.PersonalInfoResponseDTO;
import de.university.staffmanagement.entity.PersonalInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonalInfoMapper {

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.role", target = "role")
    PersonalInfoResponseDTO toDTO(PersonalInfo personalInfo);
    PersonalInfo toEntity (PersonalInfoRequestDTO personalInfoRequestDTO);
}
