package com.delivery.store.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.delivery.store.model.entity.Store;

@Repository
public interface StoreRepository extends CrudRepository<Store, Long> {

}
