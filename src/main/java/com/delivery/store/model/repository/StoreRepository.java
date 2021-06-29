package com.delivery.store.model.repository;

import org.springframework.data.repository.CrudRepository;
import com.delivery.store.model.entity.Store;

public interface StoreRepository extends CrudRepository<Store, Long> {

}
