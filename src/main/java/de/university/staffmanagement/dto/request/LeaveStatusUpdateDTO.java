package de.university.staffmanagement.dto.request;

import de.university.staffmanagement.enums.Status;
import lombok.Data;

@Data
public class LeaveStatusUpdateDTO {
    private Status newStatus;
    private String managerComment;
}
