package de.university.staffmanagement.enums;

/**
 * Enum representing different types of work shift schedules.
 *
 * <ul>
 *     <li>{@code MORNING_SHIFT} – Typically starts in the morning (e.g., 08:00)</li>
 *     <li>{@code AFTERNOON_SHIFT} – Starts in the afternoon</li>
 *     <li>{@code NIGHT_SHIFT} – Overnight or late evening shifts</li>
 *     <li>{@code FLEXIBLE} – Flexible working hours</li>
 *     <li>{@code OFF_DUTY} – No shift assigned for the day</li>
 * </ul>
 */
public enum ScheduleType {
    MORNING_SHIFT,
    AFTERNOON_SHIFT,
    NIGHT_SHIFT,
    FLEXIBLE,
    OFF_DUTY
}
