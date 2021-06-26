package com.delivery.store.service;

import java.util.NoSuchElementException;

import com.delivery.store.domain.Store;
import com.delivery.store.repository.StoreRepository;
import com.delivery.store.dto.request.StoreRequestDto;
import com.delivery.store.dto.response.StoreResponseDto;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreResponseDto find(long storeId) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new NoSuchElementException("해당 storeId가 존재하지 않습니다. storeId : " + storeId));
        return new StoreResponseDto(store);
    }

    public Store create(StoreRequestDto store) {
        return storeRepository.save(store.toEntity());
    }

    @Transactional
    public void update(long storeId, StoreRequestDto storeRequest) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new NoSuchElementException("해당 storeId가 존재하지 않습니다. storeId : " + storeId));

        store.updateInformation(storeRequest);
    }

    @Transactional
    public void delete(long storeId) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new NoSuchElementException("해당 storeId가 존재하지 않습니다. storeId : " + storeId));
        store.disableStore();
    }
}
