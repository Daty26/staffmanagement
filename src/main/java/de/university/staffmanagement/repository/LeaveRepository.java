package de.university.staffmanagement.repository;

import de.university.staffmanagement.entity.LeaveRequest;
import de.university.staffmanagement.entity.User;
import de.university.staffmanagement.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveRequest, Long>{
    List<LeaveRequest> findByStatus(Status status);
    List<LeaveRequest> findByUser(User user);
    List<LeaveRequest> findByStatusAndUser(Status status, User user);
    Long user(User user);
}
