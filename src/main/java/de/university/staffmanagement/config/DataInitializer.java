package de.university.staffmanagement.config;

import de.university.staffmanagement.entity.*;
import de.university.staffmanagement.enums.Role;
import de.university.staffmanagement.enums.ScheduleType;
import de.university.staffmanagement.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Initializes users, personal info, one shift, and 15 clock entries (yesterday to -15 days).
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PersonalInfoRepository personalInfoRepository;
    private final ShiftAssignmentRepository shiftAssignmentRepository;
    private final ClockEntryRepository clockEntryRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PersonalInfoRepository personalInfoRepository, ShiftAssignmentRepository shiftAssignmentRepository,ClockEntryRepository clockEntryRepository,  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.personalInfoRepository = personalInfoRepository;
        this.shiftAssignmentRepository = shiftAssignmentRepository;
        this.clockEntryRepository = clockEntryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {

            // Create manager
            User manager = createUser("ktsa1", "ktsa1@ktsa.de", "1234", Role.MANAGER);
            createPersonalInfo(manager, "Anna Schmidt", "Musterstraße 1, 95028 Hof", "0151-0000001", LocalDate.of(1980, 5, 12));
            createPastClockEntries(manager, LocalTime.of(8, 0), LocalTime.of(16, 0));

            // Create 10 employees
            for (int i = 2; i <= 11; i++) {
                String username = "ktsa" + i;
                User employee = createUser(username, username + "@ktsa.de", "1234", Role.EMPLOYEE);

                String fullName = "Max Mitarbeiter " + i;
                String address = "Beispielweg " + i + ", 95028 Hof";
                String phone = "0151-00000" + i;
                LocalDate birthDate = LocalDate.of(1990 + i, i % 12 + 1, i % 28 + 1);

                createPersonalInfo(employee, fullName, address, phone, birthDate);
                assignSingleShift(employee, LocalTime.of(9, 0), LocalTime.of(17, 0), ScheduleType.MORNING_SHIFT);
                createPastClockEntries(employee, LocalTime.of(9, 0), LocalTime.of(18, 0));
            }

            System.out.println("Initialized users with  clock entries.");
        } else {
            System.out.println("Users already exist, skipping initialization.");
        }
    }

    private User createUser(String username, String email, String rawPassword, Role role) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(role);
        return userRepository.save(user);
    }

    private void createPersonalInfo(User user, String fullName, String address, String phone, LocalDate birthDate) {
        PersonalInfo info = new PersonalInfo();
        info.setUser(user);
        info.setFullName(fullName);
        info.setAddress(address);
        info.setPhoneNumber(phone);
        info.setBirthDate(birthDate);
        personalInfoRepository.save(info);
    }

    private void assignSingleShift(User user, LocalTime start, LocalTime end, ScheduleType type) {
        ShiftAssignment shift = new ShiftAssignment();
        shift.setUser(user);
        shift.setShiftDate(LocalDate.now()); // Single shift for today only
        shift.setStartTime(start);
        shift.setEndTime(end);
        shift.setShiftType(type);
        shiftAssignmentRepository.save(shift);
    }

    private void createPastClockEntries(User user, LocalTime start, LocalTime end) {
        LocalDate today = LocalDate.now();

        for (int i = 1; i <= 15; i++) {
            LocalDate date = today.minusDays(i);

            ClockEntry clock = new ClockEntry();
            clock.setUser(user);
            clock.setClockInTime(LocalDateTime.of(date, start.plusMinutes(5)));
            clock.setClockOutTime(LocalDateTime.of(date, end.minusMinutes(5)));
            clockEntryRepository.save(clock);
        }
    }
}
