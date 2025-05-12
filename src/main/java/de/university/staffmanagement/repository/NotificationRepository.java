package de.university.staffmanagement.repository;

import de.university.staffmanagement.entity.LeaveRequest;
import de.university.staffmanagement.entity.Notification;
import de.university.staffmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser(User user);

}
