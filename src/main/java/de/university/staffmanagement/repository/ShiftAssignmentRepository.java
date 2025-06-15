package de.university.staffmanagement.repository;

import de.university.staffmanagement.entity.ShiftAssignment;
import de.university.staffmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ShiftAssignmentRepository extends JpaRepository<ShiftAssignment, Long> {
    List<ShiftAssignment> findByUser(User user);
    List<ShiftAssignment> findByUserAndShiftDateBetween(User user, LocalDate start, LocalDate end);
}
