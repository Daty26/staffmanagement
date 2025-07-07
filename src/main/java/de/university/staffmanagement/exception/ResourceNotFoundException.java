package de.university.staffmanagement.exception;

/**
 * Exception thrown when a requested resource (e.g., user, shift, or leave request)
 * could not be found in the system.
 *
 * <p>This is typically mapped to a 404 Not Found HTTP response.
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining which resource was not found
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
