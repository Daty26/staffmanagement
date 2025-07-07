package de.university.staffmanagement.enums;

/**
 * Enum representing possible attendance classifications for an employee's daily log.
 *
 * <ul>
 *     <li>{@code REGULAR} – Present and completed expected working hours</li>
 *     <li>{@code ON_LEAVE} – Absent due to an approved leave</li>
 *     <li>{@code ABSENT} – Not present without leave</li>
 *     <li>{@code INSUFFICIENT} – Present but did not complete required hours</li>
 * </ul>
 */
public enum AttendanceStatus {
    REGULAR, ON_LEAVE, ABSENT, INSUFFICIENT
}
