package de.university.staffmanagement.exception;

/**
 * Custom runtime exception used for general application-level errors.
 *
 * <p>This exception is typically thrown when a business rule is violated or
 * a specific operation fails without needing a more specific exception class.
 */
public class GeneralException extends RuntimeException {
    /**
     * Constructs a new GeneralException with the specified error message.
     *
     * @param msg the detail message explaining the reason for the exception
     */
    public GeneralException(String msg) {
        super(msg);
    }
}
