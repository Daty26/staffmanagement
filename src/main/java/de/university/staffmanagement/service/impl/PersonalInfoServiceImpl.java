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

    @Override
    public PersonalInfoResponseDTO update(PersonalInfoRequestDTO personalInfoRequestDTO) {
        User user = userRepository.findById(personalInfoRequestDTO.getUserId())
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

    @Override
    public PersonalInfoResponseDTO get(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new GeneralException("User not found"));

        return personalInfoRepository.findByUser(user)
                .map(personalInfoMapper::toDTO)
                .orElseGet(() -> new PersonalInfoResponseDTO(user));
    }

    @Override
    public List<PersonalInfoResponseDTO> getAll() {
        return personalInfoRepository.findAll()
                .stream()
                .map(personalInfoMapper::toDTO)
                .collect(Collectors.toList());
    }

}
