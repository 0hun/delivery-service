package com.delivery.store.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.delivery.store.model.entity.StoreEntity;

@Repository
public interface StoreRepository extends CrudRepository<StoreEntity, Long> {

}
