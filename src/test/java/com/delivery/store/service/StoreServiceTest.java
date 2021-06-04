package com.delivery.store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.delivery.store.model.StoreEnableStatus;
import com.delivery.store.model.entity.StoreEntity;
import com.delivery.store.model.repository.StoreRepository;
import com.delivery.store.model.request.StoreRequestDto;
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
        // given
        StoreEntity storeEntity = StoreEntity.builder()
            .id(1L)
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 27")
            .managerName("황윤호")
            .businessNumber("123123933")
            .storeEnableStatus(StoreEnableStatus.ENABLED)
            .build();
        given(storeRepository.findById(1L)).willReturn(Optional.of(storeEntity));

        // when
        StoreResponseDto store = storeService.findStore(1L);

        // then
        assertThat(storeEntity.getName()).isEqualTo(store.getName());
    }

    @DisplayName("store_단건_생성")
    @Test
    void creareStore() {
        // given
        StoreRequestDto storeRequest = StoreRequestDto.builder()
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 27")
            .managerName("황윤호")
            .businessNumber("123123933")
            .build();

        // when
        storeService.createStore(storeRequest);

        // then
        then(storeRepository).should().save(any());
    }

    @DisplayName("store_단건_수정")
    @Test
    void updateStore() {
        // given
        StoreRequestDto storeRequest = StoreRequestDto.builder()
            .name("윤호네곱창집")
            .telephone("02-1234-5778")
            .address("서울 송파구 송파1로 52")
            .managerName("윤호")
            .businessNumber("123123931")
            .build();

        StoreEntity store = StoreEntity.builder()
            .id(1L)
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 27")
            .managerName("황윤호")
            .businessNumber("123123933")
            .storeEnableStatus(StoreEnableStatus.ENABLED)
            .build();
        given(storeRepository.findById(1L)).willReturn(Optional.of(store));

        // when
        storeService.updateStore(1L, storeRequest);

        // then
        assertThat(store.getName()).isEqualTo(storeRequest.getName());
    }

    @DisplayName("store_단건_비활성화")
    @Test
    void deleteStore() {
        // given
        StoreEntity store = StoreEntity.builder()
            .id(1L)
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 27")
            .managerName("황윤호")
            .businessNumber("123123933")
            .build();

        given(storeRepository.findById(1L)).willReturn(Optional.of(store));

        // when
        storeService.deleteStore(1L);

        // then
        assertThat(store.getStoreEnableStatus()).isEqualTo(StoreEnableStatus.DISABLED);
    }
}
