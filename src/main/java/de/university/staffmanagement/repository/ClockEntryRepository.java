package de.university.staffmanagement.repository;

import de.university.staffmanagement.entity.LeaveRequest;
import de.university.staffmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import de.university.staffmanagement.entity.ClockEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClockEntryRepository  extends JpaRepository<ClockEntry, Long> {

//    @Query("SELECT c FROM ClockEntry c WHERE c.user.userId = :userId AND c.clockOutTime IS NULL ORDER BY c.entryId ASC")
    Optional<ClockEntry> findByUser_UserIdAndClockOutTimeIsNull(Long userId);
    List<ClockEntry> findByUser(User user);
    List<ClockEntry> findByUser_UserId(Long userId);


}
