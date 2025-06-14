package de.university.staffmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    public ConfirmationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        expiredAt = createdAt.plus(EXPIRATION_TIME_IN_HOURS, ChronoUnit.HOURS);
    }

    public void setDates(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        expiredAt = createdAt.plus(EXPIRATION_TIME_IN_HOURS, ChronoUnit.HOURS);
    }
}

