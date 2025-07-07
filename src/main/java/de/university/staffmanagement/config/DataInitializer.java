package de.university.staffmanagement.config;

import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.enums.Role;
import de.university.staffmanagement.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("manager");
            admin.setEmail("manager@example.com");
            admin.setPassword(passwordEncoder.encode("manager123"));
            admin.setRole(Role.MANAGER);

            User employee = new User();
            employee.setUsername("employee");
            employee.setEmail("employee@example.com");
            employee.setPassword(passwordEncoder.encode("emp123"));
            employee.setRole(Role.EMPLOYEE);

            userRepository.save(admin);
            userRepository.save(employee);

            System.out.println("Initialized admin and employee users.");
        } else {
            System.out.println("Users already exist, skipping initialization.");
        }
    }
}
