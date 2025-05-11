package de.university.staffmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import de.university.staffmanagement.entity.ClockEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClockEntryRepository  extends JpaRepository<ClockEntry, Long> {

    @Query("select c from ClockEntry c where c.clockOutTime IS NULL order by c.entryId asc")
    Optional<ClockEntry> findClockoutNull();
}
