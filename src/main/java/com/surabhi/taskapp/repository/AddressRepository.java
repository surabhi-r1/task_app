package com.surabhi.taskapp.repository;

import com.surabhi.taskapp.entity.AddressEntity;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<AddressEntity,Long> {
}
