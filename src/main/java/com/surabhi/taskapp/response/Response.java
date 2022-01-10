package com.surabhi.taskapp.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<RES> {
    private String message;
    private HttpStatus httpStatus;
    private RES Response;
}
