package com.delivery.store.service;


import org.springframework.stereotype.Service;

import com.delivery.store.model.entity.StoreEntity;
import com.delivery.store.model.repository.StoreRepository;
import com.delivery.store.model.response.StoreResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreResponseDto findStore(Long storeId) {
        StoreEntity store = storeRepository.findById(storeId)
            .orElseThrow(() -> new IllegalStateException("해당 storeId가 존재하지 않습니다. storeId : " + storeId));
        return new StoreResponseDto(store);
    }
}
