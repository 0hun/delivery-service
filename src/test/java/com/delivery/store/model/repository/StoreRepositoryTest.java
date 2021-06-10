package com.delivery.store.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.delivery.store.model.StoreEnableStatus;
import com.delivery.store.model.entity.StoreEntity;
import com.delivery.store.model.request.StoreRequestDto;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;
    private StoreRequestDto storeRequestDto;

    @BeforeEach
    void initData() {
        storeRequestDto = StoreRequestDto.builder()
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 27")
            .managerName("황윤호")
            .businessNumber("123123933")
            .build();
        storeRepository.save(storeRequestDto.toEntity());
    }

    @DisplayName("Store 조회 테스트")
    void find() {
        // given

        // when
        StoreEntity store = storeRepository.findById(1L)
            .orElse(null);

        // then
        assertThat(store.getName()).isEqualTo(storeRequestDto.getName());
    }

    @DisplayName("Store 생성 테스트")
    @Test
    void create() {
        // given

        // when
        List<StoreEntity> stores = (List<StoreEntity>) storeRepository.findAll();

        // then
        StoreEntity store = stores.get(0);
        assertThat(store.getName()).isEqualTo(storeRequestDto.getName());
    }


    @DisplayName("Store 수정 테스트")
    void update() {
        // given
        StoreRequestDto storeRequest = StoreRequestDto.builder()
            .name("곱돌이네2")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 27")
            .managerName("황윤호")
            .businessNumber("123123933")
            .build();
        StoreEntity store = storeRepository.findById(1L)
            .orElse(null);

        // when
        store.updateInformation(storeRequest);

        // then
        assertThat(store.getName()).isEqualTo(storeRequest.getName());
    }

    @DisplayName("Store 삭제 테스트")
    void delete() {
        // given
        StoreEntity store = storeRepository.findById(1L)
            .orElse(null);

        // when
        store.disableStore();

        // then
        assertThat(store.getStoreEnableStatus()).isEqualTo(StoreEnableStatus.DISABLED);
    }
}