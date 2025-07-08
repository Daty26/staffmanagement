package de.university.staffmanagement.mapper;


import de.university.staffmanagement.dto.request.NotificationRequestDTO;
import de.university.staffmanagement.dto.response.NotificationResponseDTO;

import de.university.staffmanagement.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting between {@link Notification} entities and their corresponding DTOs.
 *
 * <p>Utilizes MapStruct for automatic mapping between the data layers.
 */
@Mapper(componentModel = "spring")
public interface NotificationMapper {
    /**
     * Converts a {@link Notification} entity to a {@link NotificationResponseDTO}.
     *
     * <p>Maps the associated user's username into the {@code username} field of the DTO.
     *
     * @param notification the notification entity
     * @return the corresponding response DTO
     */
    @Mapping(source = "user.username", target = "username")
    NotificationResponseDTO toDTO(Notification notification);
    /**
     * Converts a {@link NotificationRequestDTO} into a {@link Notification} entity.
     *
     * @param notificationRequestDTO the request DTO containing message and username
     * @return the corresponding Notification entity
     */
    Notification toEntity(NotificationRequestDTO notificationRequestDTO);
}