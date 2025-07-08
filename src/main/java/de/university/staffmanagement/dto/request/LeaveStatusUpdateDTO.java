package de.university.staffmanagement.dto.request;

import de.university.staffmanagement.enums.Status;
import lombok.Data;

/**
 * DTO used for updating the status of a leave request.
 *
 * <p>Includes the new status and an optional comment from the manager.
 */
@Data
public class LeaveStatusUpdateDTO {
    private Status newStatus;
    private String managerComment;
}
