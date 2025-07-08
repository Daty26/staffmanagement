package de.university.staffmanagement.repository;

import de.university.staffmanagement.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Repository interface for performing CRUD operations on {@link RefreshToken} entities.
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    /**
     * Finds a {@link RefreshToken} entity by its token value.
     *
     * @param token the token string
     * @return an {@link Optional} containing the found {@link RefreshToken}, or empty if not found
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Finds a {@link RefreshToken} by the associated user's username.
     *
     * @param username the username of the user
     * @return an {@link Optional} containing the {@link RefreshToken}, or empty if not found
     */
    Optional<RefreshToken> findByUserUsername(String username);
}