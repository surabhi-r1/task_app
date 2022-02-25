package com.surabhi.taskapp.controller;

import com.surabhi.taskapp.MyUserDetailsService;
import com.surabhi.taskapp.UserDetail;
import com.surabhi.taskapp.dto.User;
import com.surabhi.taskapp.models.AuthenticationRequest;
import com.surabhi.taskapp.models.AuthenticationResponse;
import com.surabhi.taskapp.response.Response;
import com.surabhi.taskapp.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;

    public UserController(MyUserDetailsService userDetailsService) {

        this.userDetailsService = userDetailsService;

    }

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    @RequestMapping("/hello")
    public String hello() {
        return "Hello World welcome to java jwt";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            LOGGER.info("api = /authenticate, method = POST, status = IN_PROGRESS");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
            LOGGER.info("api = /authenticate, method = POST, status = SUCCESS");

        } catch (BadCredentialsException e) {
            LOGGER.error("operation = authenticate, status = ERROR, msg = error in authentication", e);

        }
        final UserDetail userDetail = userDetailsService.loadUserByUseremail(authenticationRequest.getEmail());
        final String jwt = jwtTokenUtil.generateToken(userDetail);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }



    @PostMapping(value = "/register")

    public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {
        LOGGER.info("api = /register, method = POST, status = IN_PROGRESS");
        Response<?> response = userDetailsService.save(user);
        LOGGER.info("api = /register, method = POST, status = SUCCESS");
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());

    }

}
