package de.university.staffmanagement.repository;

import de.university.staffmanagement.entity.LeaveRequest;
import de.university.staffmanagement.entity.Notification;
import de.university.staffmanagement.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing {@link Notification} entities.
 *
 * <p>Provides methods to retrieve notifications by user.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    /**
     * Finds all notifications sent to a specific user.
     *
     * @param user the recipient user
     * @return list of notifications for the given user
     */
    List<Notification> findByUser(User user);

}
