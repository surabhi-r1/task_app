package com.surabhi.taskapp.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@ControllerAdvice
public class ControllerAdviceController {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException badCredentialsException, WebRequest request) {
        Map<String, String> map = new HashMap<>();
        map.put("message", "invalid credentials");
        logMessage(badCredentialsException, request);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
    }

    private void logMessage(Exception exception, WebRequest request) {
        String requestUri = Optional.ofNullable(((ServletWebRequest) request).getRequest().getRequestURI()).orElse(StringUtils.EMPTY);
        HttpMethod httpMethod = Optional.ofNullable(((ServletWebRequest) request).getHttpMethod()).orElse(null);
        log.error("api = {}, method = {}, result = ERROR = ", requestUri, httpMethod, exception);
    }
}
