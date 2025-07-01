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

@Repository
public interface LeaveRepository extends JpaRepository<LeaveRequest, Long>{
    List<LeaveRequest> findByStatus(Status status);
    List<LeaveRequest> findByUser(User user);
    List<LeaveRequest> findByStatusAndUser(Status status, User user);
    Long user(User user);
    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.user = :user AND lr.status = 'APPROVED' " +
            "AND lr.endDate >= :startDate AND lr.startDate <= :endDate")
        List<LeaveRequest> findApprovedByUserAndDateRange(
                @Param("user") User user,
                @Param("startDate") LocalDate startDate,
                @Param("endDate") LocalDate endDate
        );

}
