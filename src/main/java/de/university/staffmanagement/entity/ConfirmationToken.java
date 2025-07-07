package de.university.staffmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Entity representing a one-time confirmation token used for account verification or password reset.
 *
 * <p>Each token is associated with a specific user and has a fixed expiration time.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private static final int EXPIRATION_TIME_IN_HOURS = 1;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Constructs a new confirmation token with an expiration time of 1 hour from creation.
     *
     * @param token the token string
     * @param user the user associated with the token
     */
    public ConfirmationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        expiredAt = createdAt.plus(EXPIRATION_TIME_IN_HOURS, ChronoUnit.HOURS);
    }
    /**
     * Sets the creation and expiration timestamps based on the given creation time.
     *
     * @param createdAt the new creation time
     */
    public void setDates(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        expiredAt = createdAt.plus(EXPIRATION_TIME_IN_HOURS, ChronoUnit.HOURS);
    }
}

