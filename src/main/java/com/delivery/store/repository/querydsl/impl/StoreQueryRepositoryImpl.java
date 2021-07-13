package com.delivery.store.repository.querydsl.impl;


import com.delivery.store.domain.QStore;
import java.util.Optional;
import com.delivery.store.domain.Store;
import com.delivery.store.repository.querydsl.StoreQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StoreQueryRepositoryImpl implements StoreQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Store> findStoreById(Long id) {
        return Optional.ofNullable(queryFactory.selectFrom(QStore.store)
            .where(QStore.store.id.eq(id))
            .fetchOne());
    }
}
