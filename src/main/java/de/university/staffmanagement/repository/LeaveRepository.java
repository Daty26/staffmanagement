package de.university.staffmanagement.repository;

import de.university.staffmanagement.entity.LeaveRequest;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Repository interface for accessing {@link LeaveRequest} entities.
 *
 * <p>Provides methods to query leave requests by user, status, and date range.
 */
@Repository
public interface LeaveRepository extends JpaRepository<LeaveRequest, Long>{
    /**
     * Finds all leave requests with the specified status.
     *
     * @param status the leave request status (e.g., APPROVED, PENDING)
     * @return list of leave requests with the given status
     */
    List<LeaveRequest> findByStatus(Status status);
    /**
     * Finds all leave requests submitted by a specific user.
     *
     * @param user the user entity
     * @return list of leave requests for the given user
     */
    List<LeaveRequest> findByUser(User user);
    /**
     * Finds all leave requests by user and status.
     *
     * @param status the leave request status
     * @param user the user entity
     * @return list of matching leave requests
     */
    List<LeaveRequest> findByStatusAndUser(Status status, User user);

    /**
     * Finds all approved leave requests for a user that fall within a given date range.
     *
     * @param user the user
     * @param startDate the start of the date range
     * @param endDate the end of the date range
     * @return list of approved leave requests within the specified range
     */

    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.user = :user AND lr.status = 'APPROVED' " +
            "AND lr.endDate >= :startDate AND lr.startDate <= :endDate")
        List<LeaveRequest> findApprovedByUserAndDateRange(
                @Param("user") User user,
                @Param("startDate") LocalDate startDate,
                @Param("endDate") LocalDate endDate
        );

}
