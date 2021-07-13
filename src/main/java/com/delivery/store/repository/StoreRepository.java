package com.delivery.store.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.delivery.store.domain.Store;
import com.delivery.store.repository.querydsl.StoreQueryRepository;

@Repository
public interface StoreRepository extends CrudRepository<Store, Long>, StoreQueryRepository {

}
