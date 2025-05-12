package de.university.staffmanagement.mapper;


import de.university.staffmanagement.dto.request.NotificationRequestDTO;
import de.university.staffmanagement.dto.response.NotificationResponseDTO;

import de.university.staffmanagement.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(source = "user.username", target = "username")
    NotificationResponseDTO toDTO(Notification notification);
    Notification toEntity(NotificationRequestDTO notificationRequestDTO);
}