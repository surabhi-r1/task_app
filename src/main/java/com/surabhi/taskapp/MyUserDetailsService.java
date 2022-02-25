package com.surabhi.taskapp;

import com.surabhi.taskapp.entity.UserEntity;
import com.surabhi.taskapp.repository.UserRepository;
import com.surabhi.taskapp.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(MyUserDetailsService.class);

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }

    public UserDetail loadUserByUseremail(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + email);
        }
        return new UserDetail(user.getName(),user.getPassword(),user.getEmail(),user.getId(),
                new ArrayList<>());
    }

    public Response<?> save(com.surabhi.taskapp.dto.User user) {
        Response<?> response = new Response<>();
        try {

            LOGGER.info("operation =save the user, status = IN_PROGRESS, message = register the user");

            UserEntity newUser = new UserEntity();
            newUser.setId(user.getId());
            newUser.setName(user.getName());
            newUser.setPassword(user.getPassword());
            newUser.setEmail(user.getEmail());

            userRepository.save(newUser);
            response.setHttpStatus(HttpStatus.CREATED);

            LOGGER.info("operation =save the user, status = SUCCESS, message = register the user");
            return response;
        } catch (Exception e) {
            LOGGER.error("operation = save the user, status = ERROR, msg = error in add details", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
