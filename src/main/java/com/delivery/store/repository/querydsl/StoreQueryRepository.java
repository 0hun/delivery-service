package com.delivery.store.repository.querydsl;

import java.util.Optional;

import com.delivery.store.domain.Store;

public interface StoreQueryRepository {

    Optional<Store> findStoreById(Long id);
}
