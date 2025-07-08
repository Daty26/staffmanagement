package de.university.staffmanagement.repository;

import de.university.staffmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import de.university.staffmanagement.entity.ClockEntry;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link ClockEntry} entities.
 *
 * <p>Provides methods for querying clock-in and clock-out records by user and time range.
 */
@Repository
public interface ClockEntryRepository  extends JpaRepository<ClockEntry, Long> {

    /**
     * Finds the most recent clock-in entry for a user that hasn't been clocked out yet.
     *
     * @param userId the user's ID
     * @return an optional containing the open clock entry if found
     */
    Optional<ClockEntry> findByUser_UserIdAndClockOutTimeIsNull(Long userId);
    /**
     * Finds all clock entries associated with a given user.
     *
     * @param user the user entity
     * @return a list of clock entries
     */
    List<ClockEntry> findByUser(User user);
    /**
     * Finds all clock entries for a user by their ID.
     *
     * @param userId the user ID
     * @return a list of clock entries
     */
    List<ClockEntry> findByUser_UserId(Long userId);
    /**
     * Finds all clock entries for a user within a specific date and time range.
     *
     * @param user  the user entity
     * @param start the start of the time range
     * @param end   the end of the time range
     * @return a list of matching clock entries
     */
    List<ClockEntry> findAllByUserAndClockInTimeBetween(User user, LocalDateTime start, LocalDateTime end);


}
