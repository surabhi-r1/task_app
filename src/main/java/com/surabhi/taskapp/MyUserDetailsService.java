package com.surabhi.taskapp;

import com.surabhi.taskapp.dto.User;
import com.surabhi.taskapp.entity.UserEntity;
import com.surabhi.taskapp.repository.UserRepository;
import com.surabhi.taskapp.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("operation =loadUserByUsername, status = IN_PROGRESS, message = load user by Username");
        UserEntity user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }

        log.info("operation =loadUserByUsername, status =SUCCESS, message = load user by Username");

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());

    }

    public UserDetail loadUserByUseremail(String email) throws UsernameNotFoundException {

        log.info("operation =loadUserByUseremail, status = IN_PROGRESS, message = load user by User email");
        UserEntity user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + email);
        }

        log.info("operation =loadUserByUseremail, status =SUCCESS, message = load user by User email");
        return new UserDetail(user.getName(), user.getPassword(), user.getEmail(), user.getId(),
                new ArrayList<>());
    }

    public Response<?> save(User user) {
        Response<?> response = new Response<>();
        try {

            log.info("operation =save the user, status = IN_PROGRESS, message = save the user");

            UserEntity newUser = new UserEntity();
            newUser.setId(user.getId());
            newUser.setName(user.getName());
            newUser.setPassword(user.getPassword());
            newUser.setEmail(user.getEmail());

            userRepository.save(newUser);
            response.setHttpStatus(HttpStatus.CREATED);

            log.info("operation =save the user, status = SUCCESS, message =save the user");
            return response;
        } catch (Exception e) {
            log.error("operation = save the user, status = ERROR, msg = error in save", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
