package de.university.staffmanagement.service.impl;

import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) {

        logger.debug("Entering in loadUserByUsername Method...");

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("Username not found: " + username);
                    return new UsernameNotFoundException("could not found user..!!");
                });

        logger.info("User Authenticated Successfully..!!!");
        return user;
    }

}
