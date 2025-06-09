package de.university.staffmanagement.dto.response;

import lombok.Data;
import lombok.Getter;

@Data
public class ResponseWrapper <T>{
    private T data;
    private String err;
    public ResponseWrapper(T data, String err) {
        this.data = data;
        this.err = err;
    }
    public ResponseWrapper(T data) {
        this.data = data;
        this.err = "";
    }
    public ResponseWrapper(String err ) {
        this.data = null;
        this.err = err;
    }
}
