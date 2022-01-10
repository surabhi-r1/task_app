package com.surabhi.taskapp.repository;

import com.surabhi.taskapp.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<UserEntity,Long> {
}
