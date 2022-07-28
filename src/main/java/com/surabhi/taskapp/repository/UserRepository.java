package com.surabhi.taskapp.repository;

import com.surabhi.taskapp.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity,String>  {
    UserEntity findByEmail(String email);
}
