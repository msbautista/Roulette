package com.masivian.roulette.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {
    private HttpStatus status;
    private Object data;
    private String error;

    public ApiResponse(Object data) {
        this.data = data;
        this.error = null;
    }

    public ResponseEntity<ApiResponse> send(HttpStatus status) {
        this.status = status;
        return new ResponseEntity<ApiResponse>(this, status);
    }

    public ResponseEntity<Object> send(HttpStatus status, String error) {
        this.status = status;
        this.error = error;
        return new ResponseEntity<>(this, status);
    }
}
