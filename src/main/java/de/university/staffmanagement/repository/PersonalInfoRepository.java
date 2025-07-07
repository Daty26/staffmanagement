package de.university.staffmanagement.repository;

import de.university.staffmanagement.entity.LeaveRequest;
import de.university.staffmanagement.entity.Notification;
import de.university.staffmanagement.entity.PersonalInfo;
import de.university.staffmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link PersonalInfo} entities.
 *
 * <p>Provides methods to retrieve personal information records by user.
 */
@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {
    /**
     * Finds the personal information record associated with the given user.
     *
     * @param user the user whose personal information is requested
     * @return an optional containing the personal information if present
     */
    Optional<PersonalInfo> findByUser(User user);
}

