package de.university.staffmanagement.dto.response;

import lombok.Data;
import lombok.Getter;

/**
 * Generic wrapper for API responses.
 *
 * <p>Encapsulates a response payload of type {@code T} and an optional error message.
 * Used to provide consistent structure for successful and failed responses.
 *
 * @param <T> the type of the data being returned
 */
@Data
public class ResponseWrapper <T>{
    private T data;
    private String err;
    /**
     * Creates a wrapper with both data and error message.
     *
     * @param data the response data
     * @param err the error message
     */
    public ResponseWrapper(T data, String err) {
        this.data = data;
        this.err = err;
    }
    /**
     * Creates a wrapper with only data (no error).
     *
     * @param data the response data
     */
    public ResponseWrapper(T data) {
        this.data = data;
        this.err = "";
    }
    /**
     * Creates a wrapper with only an error message (no data).
     *
     * @param err the error message
     */
    public ResponseWrapper(String err ) {
        this.data = null;
        this.err = err;
    }
}
