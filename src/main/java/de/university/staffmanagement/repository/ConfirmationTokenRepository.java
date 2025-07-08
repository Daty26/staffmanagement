package de.university.staffmanagement.repository;

import de.university.staffmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import de.university.staffmanagement.entity.ConfirmationToken;

import java.util.Optional;

/**
 * Repository interface for accessing {@link ConfirmationToken} entities.
 *
 * <p>Provides methods to retrieve confirmation tokens by user or token string.
 */
@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    /**
     * Retrieves a confirmation token associated with the given user.
     *
     * @param user the user entity
     * @return an optional containing the confirmation token, if found
     */
    Optional<ConfirmationToken> findByUser(User user);
    /**
     * Retrieves a confirmation token by its token string value.
     *
     * @param token the token string
     * @return an optional containing the confirmation token, if found
     */
    Optional<ConfirmationToken> findByToken(String token);
}