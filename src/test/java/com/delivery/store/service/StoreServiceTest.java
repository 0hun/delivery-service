package com.delivery.store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Optional;

import com.delivery.store.model.request.StoreRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.delivery.store.model.entity.StoreEntity;
import com.delivery.store.model.repository.StoreRepository;
import com.delivery.store.model.response.StoreResponseDto;

class StoreServiceTest {

    private StoreService storeService;

    @Mock
    private StoreRepository storeRepository;

    @BeforeEach
    public void initMock() {
        MockitoAnnotations.openMocks(this);
        storeService = new StoreService(storeRepository);
    }

    @DisplayName("store_단건_조회")
    @Test
    void findStore() {
        StoreEntity storeEntity = StoreEntity.builder()
            .id(1L)
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 27")
            .managerName("황윤호")
            .businessNumber("123123933")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        given(storeRepository.findById(1L)).willReturn(Optional.of(storeEntity));

        StoreResponseDto store = storeService.findStore(1L);
        assertThat(storeEntity.getName()).isEqualTo(store.getName());
    }

    @Test
    public void creareStore() {
        StoreRequestDto storeRequest = StoreRequestDto.builder()
                .name("곱돌이네")
                .telephone("02-1234-5678")
                .address("서울 송파구 송파1로 27")
                .managerName("황윤호")
                .businessNumber("123123933")
                .build();

        storeService.createStore(storeRequest);

        verify(storeRepository).save(any());
    }
}
