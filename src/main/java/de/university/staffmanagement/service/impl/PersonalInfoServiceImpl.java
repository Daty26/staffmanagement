package de.university.staffmanagement.service.impl;

import de.university.staffmanagement.dto.request.PersonalInfoRequestDTO;
import de.university.staffmanagement.dto.response.PersonalInfoResponseDTO;
import de.university.staffmanagement.entity.PersonalInfo;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.enums.Role;
import de.university.staffmanagement.exception.GeneralException;
import de.university.staffmanagement.mapper.PersonalInfoMapper;
import de.university.staffmanagement.repository.PersonalInfoRepository;
import de.university.staffmanagement.repository.UserRepository;
import de.university.staffmanagement.service.NotificationService;
import de.university.staffmanagement.service.PersonalInfoService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Implementation of the {@link de.university.staffmanagement.service.PersonalInfoService} interface.
 *
 * <p>This service handles the retrieval and update of personal information
 * for users in the staff management system.
 *
 * <p>Main functionalities include:
 * <ul>
 *     <li>Creating or updating personal information (name, address, etc.)</li>
 *     <li>Fetching personal info for a specific user or all users</li>
 * </ul>
 *
 * <p>Uses {@link PersonalInfoRepository} and {@link UserRepository} for persistence,
 * and {@link PersonalInfoMapper} for mapping between entities and DTOs.
 */
@Service
public class PersonalInfoServiceImpl implements PersonalInfoService {

    private PersonalInfoRepository personalInfoRepository;
    private final UserRepository userRepository;
    private PersonalInfoMapper personalInfoMapper;
    private static final Logger logger = LoggerFactory.getLogger(PersonalInfoServiceImpl.class);


    public PersonalInfoServiceImpl(PersonalInfoRepository personalInfoRepository, PersonalInfoMapper personalInfoMapper, UserRepository userRepository) {
        this.personalInfoRepository = personalInfoRepository;
        this.personalInfoMapper = personalInfoMapper;
        this.userRepository = userRepository;
    }

    /**
     * Updates the personal information of the authorized user.
     * If no personal information exists yet, a new one is created.
     *
     * @param personalInfoRequestDTO contains updated user info (name, address, etc.)
     * @param authorizedUser the user whose data is being updated (authenticated user)
     * @return the updated personal information as a DTO
     * @throws RuntimeException if the user is not found in the repository
     */
    @Override
    public PersonalInfoResponseDTO update(PersonalInfoRequestDTO personalInfoRequestDTO, User authorizedUser) {
        User user = userRepository.findById(authorizedUser.getUserId())
                .orElseThrow(() -> new RuntimeException("User Id not found"));
        //creates the profile page if it's not created
        PersonalInfo personalInfo = personalInfoRepository.findByUser(user).orElseGet(() -> {
            PersonalInfo newInfo = new PersonalInfo();
            newInfo.setUser(user);
            return newInfo;
        });

        personalInfo.setFullName(personalInfoRequestDTO.getFullName());
        personalInfo.setPhoneNumber(personalInfoRequestDTO.getPhoneNumber());
        personalInfo.setAddress(personalInfoRequestDTO.getAddress());
        personalInfo.setBirthDate(personalInfoRequestDTO.getBirthDate());

        personalInfoRepository.save(personalInfo);
        return personalInfoMapper.toDTO(personalInfo);
    }

    /**
     * Retrieves the personal information of the currently authorized user.
     * If no information exists yet, a response DTO is generated from the user entity.
     *
     * @param authorizedUser the currently authenticated user
     * @return the personal information of the user as a DTO
     * @throws GeneralException if the user is not found
     */

    @Override
    public PersonalInfoResponseDTO get(User authorizedUser) {
        User user = userRepository.findById(authorizedUser.getUserId())
                .orElseThrow(() -> new GeneralException("User not found"));

        return personalInfoRepository.findByUser(user)
                .map(personalInfoMapper::toDTO)
                .orElseGet(() -> new PersonalInfoResponseDTO(user));
    }

    /**
     * Retrieves the personal information of all users in the system.
     *
     * @return a list of personal information DTOs for all users
     */
    @Override
    public List<PersonalInfoResponseDTO> getAll() {
        return personalInfoRepository.findAll()
                .stream()
                .map(personalInfoMapper::toDTO)
                .collect(Collectors.toList());
    }

}
