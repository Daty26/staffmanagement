package de.university.staffmanagement.repository;


import de.university.staffmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Checks whether a user with the specified username exists.
     *
     * @param username the username to check
     * @return {@code true} if a user with the username exists, {@code false} otherwise
     */
    Boolean existsByUsername(String username);

    /**
     * Checks whether a user with the specified email exists.
     *
     * @param email the email to check
     * @return {@code true} if a user with the email exists, {@code false} otherwise
     */
    Boolean existsByEmail(String email);
    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return an {@link Optional} containing the found {@link User}, or empty if not found
     */
    Optional<User> findByUsername(String username);
}

