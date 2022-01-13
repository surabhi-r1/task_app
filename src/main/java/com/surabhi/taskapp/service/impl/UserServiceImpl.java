package com.surabhi.taskapp.service.impl;

import com.surabhi.taskapp.dto.Address;
import com.surabhi.taskapp.dto.User;
import com.surabhi.taskapp.entity.AddressEntity;
import com.surabhi.taskapp.entity.UserEntity;
import com.surabhi.taskapp.repository.AddressRepository;
import com.surabhi.taskapp.repository.UserRepository;
import com.surabhi.taskapp.response.PaginationResponse;
import com.surabhi.taskapp.response.Response;
import com.surabhi.taskapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;
 //   private final AddressRepository addressRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
      //  this.addressRepository = addressRepository;
    }

    @Override
    public Response<PaginationResponse<List<User>>> getAll(Pageable pageable) {
        Response<PaginationResponse<List<User>>> response = new Response<>();
        log.info("operation = getAllInfo, status = IN_PROGRESS, message = get all info");
        try {

            Page<UserEntity> page = userRepository.findAll(pageable);
            PaginationResponse<List<User>> paginationResponse = new PaginationResponse<>();
            paginationResponse.setTotalPages(page.getTotalPages());
            paginationResponse.setTotalElements(page.getTotalElements());
            paginationResponse.setSize(pageable.getPageSize());

            List<User> users = new ArrayList<>();
            for (UserEntity userEntity : page.getContent()) {
                User user = new User();
                BeanUtils.copyProperties(userEntity, user);


                Set<Address> addresses = Optional.ofNullable(userEntity.getAddressEntityList())
                        .orElse(Collections.emptySet())
                        .stream().map(addressEntity -> {
                            Address address = new Address();
                            BeanUtils.copyProperties(addressEntity, address);
                            return address;
                        })
                        .collect(Collectors.toSet());
                user.setAddresses(addresses);
                users.add(user);
            }
            log.info("operation = getAll, status = SUCCESS, message = get all info");
            response.setHttpStatus(HttpStatus.OK);
            paginationResponse.setData(users);
            response.setResponse(paginationResponse);
            return response;
        } catch (Exception e) {
            log.error("operation = getAllInfo, status = ERROR, msg = error in getAllInfo", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    @Override
    public Response<?> add(User user) {
        log.info("operation = add, status = IN_PROGRESS, message = add all info");
        Response<?> response = new Response<>();
        try {
            UserEntity userEntity = new UserEntity();
            BeanUtils.copyProperties(user, userEntity);
           // UserEntity entity = userRepository.save(userEntity);


            Set<AddressEntity> addressEntitySet = new HashSet<>();
            if (user.getAddresses() != null && !user.getAddresses().isEmpty()) {
                for (Address address : user.getAddresses()) {
                    AddressEntity addressEntity = new AddressEntity();
                    BeanUtils.copyProperties(address, addressEntity);
                   addressEntitySet.add(addressEntity);
                }
            }

            userEntity.setAddressEntityList(addressEntitySet);
            userRepository.save(userEntity);
            log.info("operation = add, status = SUCCESS, message = add all info");
            response.setHttpStatus(HttpStatus.CREATED);
            return response;
        } catch (Exception e) {
            log.error("operation = add, status = ERROR, msg = error in add", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    @Transactional
    public Response<?> update(Long id, User user) {
        Response<?> response = new Response<>();
        log.info("operation = update, status = IN_PROGRESS, message = update user info");
        try {
            Optional<UserEntity> entity = userRepository.findById(id);
            if (entity.isPresent()) {
                UserEntity userEntity = entity.get();

                Optional.ofNullable(user.getName())
                        .ifPresent(userEntity::setName);

                Optional.ofNullable(user.getRegNo())
                        .ifPresent(userEntity::setRegNo);

                Optional.ofNullable(user.getAge())
                        .ifPresent(userEntity::setAge);

                Optional.ofNullable(user.getGender())
                        .ifPresent(userEntity::setGender);

                Optional.ofNullable(user.getMobileNumber())
                        .ifPresent(userEntity::setMobileNumber);

                userRepository.save(userEntity);
                log.info("operation = update, status = SUCCESS, message = update user info");
                response.setHttpStatus(HttpStatus.OK);

            } else {
                response.setHttpStatus(HttpStatus.NOT_FOUND);
            }
            return response;
        } catch (Exception e) {
            log.error("operation = update, status = ERROR, msg = error in update ", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }

    @Override
    public Response<User> getById(Long id) {
        Response<User> response = new Response<>();
        log.info("operation = getById, status = IN_PROGRESS, message = update user info");
        try {
            Optional<UserEntity> userEntity = userRepository.findById(id);
            if (userEntity.isPresent()) {
                User user = new User();
                BeanUtils.copyProperties(userEntity.get(), user);

                log.info("operation = getById, status = SUCCESS, message = update user info");
                response.setHttpStatus(HttpStatus.OK);
                response.setResponse(user);

            } else {
                response.setHttpStatus(HttpStatus.NOT_FOUND);
            }
            return response;
        } catch (Exception e) {
            log.error("operation = getById, status = ERROR, msg = error in get task by id", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return response;

        }
    }

    @Override
    public Response<?> delete(Long id) {
        Response<?> response = new Response<>();
        log.info("operation = delete, status = IN_PROGRESS, message = delete info by id");
        try {
            userRepository.deleteById(id);
            log.info("operation = delete, status = SUCCESS, message = delete info by id");
            response.setHttpStatus(HttpStatus.OK);
            return response;

        } catch (Exception e) {
            log.error("operation = delete, status = ERROR, msg = error in delete", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }


}
