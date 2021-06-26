package com.delivery.store.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.delivery.store.domain.Store;

@Repository
public interface StoreRepository extends CrudRepository<Store, Long> {

}
