package com.surabhi.taskapp.controller;

import com.surabhi.taskapp.MyUserDetailsService;
import com.surabhi.taskapp.UserDetail;
import com.surabhi.taskapp.dto.User;
import com.surabhi.taskapp.models.AuthenticationRequest;
import com.surabhi.taskapp.models.AuthenticationResponse;
import com.surabhi.taskapp.response.Response;
import com.surabhi.taskapp.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UserController {

    private final AuthenticationManager authenticationManager;

    private final MyUserDetailsService userDetailsService;

    private final JwtUtil jwtTokenUtil;

    public UserController(AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService, JwtUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World welcome to java jwt";
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception  {
        log.info("api = /authenticate, method = POST, status = IN_PROGRESS");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
        log.info("api = /authenticate, method = POST, status = SUCCESS");
        final UserDetail userDetail = userDetailsService.loadUserByUseremail(authenticationRequest.getEmail());
        final String jwt = jwtTokenUtil.generateToken(userDetail);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthenticationResponse(jwt));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {
        log.info("api = /register, method = POST, status = IN_PROGRESS");
        Response<?> response = userDetailsService.save(user);
        log.info("api = /register, method = POST, status = SUCCESS");
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());

    }

}
