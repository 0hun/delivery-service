package com.delivery.store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.delivery.store.model.StoreEnableStatus;
import com.delivery.store.model.entity.Store;
import com.delivery.store.model.repository.StoreRepository;
import com.delivery.store.model.request.StoreRequestDto;
import com.delivery.store.model.response.StoreResponseDto;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @InjectMocks
    private StoreService storeService;

    @Mock
    private StoreRepository storeRepository;

    @DisplayName("store_단건_조회")
    @Test
    void find() {
        // given
        Store storeEntity = Store.builder()
            .id(1L)
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 4427")
            .managerName("황윤호")
            .businessNumber("123-33-12345")
            .storeEnableStatus(StoreEnableStatus.ENABLED)
            .build();

        given(storeRepository.findById(1L)).willReturn(Optional.of(storeEntity));

        // when
        StoreResponseDto store = storeService.find(1L);

        // then
        assertThat(storeEntity.getName()).isEqualTo(store.getName());
    }

    @DisplayName("store_단건_조회_실패")
    @Test
    void findFailure() {
        // given
        given(storeRepository.findById(1L)).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> {
            storeService.find(1L);
        }).isInstanceOf(NoSuchElementException.class)
            .hasMessage("해당 storeId가 존재하지 않습니다. storeId : 1");
    }

    @DisplayName("store_단건_생성")
    @Test
    void create() {
        // given
        StoreRequestDto storeRequest = StoreRequestDto.builder()
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 4427")
            .managerName("황윤호")
            .businessNumber("123-33-12345")
            .build();

        // when
        storeService.create(storeRequest);

        // then
        then(storeRepository).should().save(any());
    }

    @DisplayName("store_단건_수정")
    @Test
    void update() {
        // given
        StoreRequestDto storeRequest = StoreRequestDto.builder()
            .name("윤호네곱창집")
            .telephone("02-1234-5778")
            .address("서울 송파구 송파1로 52")
            .managerName("윤호")
            .businessNumber("123-33-12345")
            .build();

        Store store = Store.builder()
            .id(1L)
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 27")
            .managerName("황윤호")
            .businessNumber("123-33-12345")
            .storeEnableStatus(StoreEnableStatus.ENABLED)
            .build();

        given(storeRepository.findById(1L)).willReturn(Optional.of(store));

        // when
        storeService.update(1L, storeRequest);

        // then
        assertThat(store.getName()).isEqualTo(storeRequest.getName());
    }

    @DisplayName("store_단건_수정_실패")
    @Test
    void updateFailure() {
        // given
        StoreRequestDto storeRequest = StoreRequestDto.builder()
            .name("윤호네곱창집")
            .telephone("02-1234-5778")
            .address("서울 송파구 송파1로 52")
            .managerName("윤호")
            .businessNumber("123-33-12345")
            .build();

        given(storeRepository.findById(1L)).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> {
            storeService.update(1L, storeRequest);
        }).isInstanceOf(NoSuchElementException.class)
            .hasMessage("해당 storeId가 존재하지 않습니다. storeId : 1");
    }


    @DisplayName("store_단건_비활성화")
    @Test
    void delete() {
        // given
        Store store = Store.builder()
            .id(1L)
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 27")
            .managerName("황윤호")
            .businessNumber("123-33-12345")
            .build();

        given(storeRepository.findById(1L)).willReturn(Optional.of(store));

        // when
        storeService.delete(1L);

        // then
        assertThat(store.getStoreEnableStatus()).isEqualTo(StoreEnableStatus.DISABLED);
    }

    @DisplayName("store_단건_비활성화_실패")
    @Test
    void deleteFailure() {
        // given
        given(storeRepository.findById(1L)).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> {
            storeService.delete(1L);
        }).isInstanceOf(NoSuchElementException.class)
            .hasMessage("해당 storeId가 존재하지 않습니다. storeId : 1");
    }
}
