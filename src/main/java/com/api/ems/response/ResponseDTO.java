package com.api.ems.response;

import org.springframework.http.ResponseEntity;

public class ResponseDTO<T> {

    private String message;
    private Integer httpStatusCode;
    private T body;

    public ResponseDTO(String message, Integer httpStatusCode, T body) {
        super();
        this.message = message;
        this.httpStatusCode = httpStatusCode;
        this.body = body;
    }

    public ResponseDTO(String message, Integer httpStatusCode) {
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    public static <T> ResponseEntity<ResponseDTO<T>> prepare(String message, Integer httpStatusCode, T body) {
        return ResponseEntity.ofNullable(new ResponseDTO<>(message, httpStatusCode, body));
    }

    public static <T> ResponseEntity<ResponseDTO<T>> prepare(String message, Integer httpStatusCode) {
        return ResponseEntity.ofNullable(new ResponseDTO<>(message, httpStatusCode));
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
