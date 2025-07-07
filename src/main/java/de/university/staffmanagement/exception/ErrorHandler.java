package de.university.staffmanagement.exception;

import de.university.staffmanagement.dto.response.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import de.university.staffmanagement.exception.ErrorResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the Staff Management System.
 *
 * <p>Handles validation errors, custom exceptions, and generic server-side failures.
 * Wraps all error responses in a consistent {@link ResponseWrapper} format.
 */
@RestControllerAdvice
public class ErrorHandler {

    /**
     * Handles validation errors thrown when request bodies fail {@code @Valid} checks.
     *
     * @param ex the exception containing validation errors
     * @return a map of field names to error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(new ResponseWrapper<>(null, "couldn't validate, please check fields "), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles custom business logic errors thrown as {@link GeneralException}.
     *
     * @param generalException the custom exception
     * @return a bad request response with the error message
     */
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ResponseWrapper<Void>> handleGeneralException(GeneralException generalException) {
        return new ResponseEntity<>(new ResponseWrapper<>(null, generalException.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles any uncaught exceptions not matched by other handlers.
     *
     * @param ex the exception
     * @return a generic internal server error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper<Void>> handleOthersEx(Exception ex) {
        return new ResponseEntity<>(new ResponseWrapper<>(null, "something is wrong"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles resource not found errors.
     *
     * @param ex the exception indicating a missing resource
     * @return a 404 not found response with the error message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseWrapper<Void>> handleNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ResponseWrapper<>(null, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse notFoundExceptionHandler(GeneralException generalException) {
//        return new ErrorResponse(generalException.getMessage());
//    }
}
