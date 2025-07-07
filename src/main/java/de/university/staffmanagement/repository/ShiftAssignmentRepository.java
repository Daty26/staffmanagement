package de.university.staffmanagement.repository;

import de.university.staffmanagement.entity.ShiftAssignment;
import de.university.staffmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for accessing and managing {@link ShiftAssignment} entities.
 */
public interface ShiftAssignmentRepository extends JpaRepository<ShiftAssignment, Long> {
    /**
     * Retrieves all shift assignments for a specific user.
     *
     * @param user the user whose shifts are to be retrieved
     * @return a list of {@link ShiftAssignment} for the given user
     */
    List<ShiftAssignment> findByUser(User user);
    /**
     * Retrieves all shift assignments for a specific user between the given dates.
     *
     * @param user  the user whose shifts are to be retrieved
     * @param start the start date of the range
     * @param end   the end date of the range
     * @return a list of {@link ShiftAssignment} within the date range for the given user
     */
    List<ShiftAssignment> findByUserAndShiftDateBetween(User user, LocalDate start, LocalDate end);
}
