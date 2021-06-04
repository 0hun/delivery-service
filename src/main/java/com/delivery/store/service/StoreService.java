package com.delivery.store.service;

import com.delivery.store.model.entity.StoreEntity;
import com.delivery.store.model.repository.StoreRepository;
import com.delivery.store.model.request.StoreRequestDto;
import com.delivery.store.model.response.StoreResponseDto;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreResponseDto findStore(Long storeId) {
        StoreEntity store = storeRepository.findById(storeId)
            .orElseThrow(() -> new IllegalStateException("해당 storeId가 존재하지 않습니다. storeId : " + storeId));
        return new StoreResponseDto(store);
    }

    public StoreEntity createStore(StoreRequestDto store) {
        return storeRepository.save(store.toEntity());
    }


    @Transactional
    public void updateStore(Long storeId, StoreRequestDto storeRequest) {
        StoreEntity store = storeRepository.findById(storeId)
            .orElseThrow(() -> new IllegalStateException("해당 storeId가 존재하지 않습니다. storeId : " + storeId));

        store.updateInformation(storeRequest);
    }

    @Transactional
    public void deleteStore(Long storeId) {
        StoreEntity store = storeRepository.findById(storeId)
            .orElseThrow(() -> new IllegalStateException("해당 storeId가 존재하지 않습니다. storeId : " + storeId));
        store.disableStore();
    }
}
