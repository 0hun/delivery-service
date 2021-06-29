package com.delivery.store.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import com.delivery.store.model.StoreEnableStatus;
import com.delivery.store.model.entity.Store;
import com.delivery.store.model.request.StoreRequestDto;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private EntityManager entityManager;

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

    /**
     * Class 전체 테스트시
     * auto-increament값을 초기화를 해줘야 한다
     */
    @AfterEach
    void increamentInit() {
        storeRepository.deleteAll();
        this.entityManager
            .createNativeQuery("ALTER TABLE store ALTER COLUMN `id` RESTART WITH 1")
            .executeUpdate();
    }

    @DisplayName("Store 조회 테스트")
    @Test
    void find() {
        // given

        // when
        Store store = storeRepository.findById(1L)
            .orElse(null);

        // then
        assertThat(store.getName()).isEqualTo(storeRequestDto.getName());
    }

    @DisplayName("Store 조회 결과 없음")
    @Test
    void find_실패() {
        // given

        // when
        Store store = storeRepository.findById(2L)
            .orElse(null);

        // then
        assertThat(store).isNull();
    }

    @DisplayName("Store 생성 테스트")
    @Test
    void create() {
        // given

        // when
        List<Store> stores = (List<Store>) storeRepository.findAll();

        // then
        Store store = stores.get(0);

        assertThat(store.getName()).isEqualTo(storeRequestDto.getName());
    }

    @DisplayName("Store 수정 테스트")
    @Test
    void update() {
        // given
        StoreRequestDto storeRequest = StoreRequestDto.builder()
            .name("곱돌이네2")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 27")
            .managerName("황윤호")
            .businessNumber("123123933")
            .build();

        Store store = storeRepository.findById(1L)
            .orElse(null);

        // when
        store.updateInformation(storeRequest);

        // then
        assertThat(store.getName()).isEqualTo(storeRequest.getName());
    }

    @DisplayName("Store 삭제 테스트")
    @Test
    void delete() {
        // given
        Store store = storeRepository.findById(1L)
            .orElse(null);

        // when
        store.disableStore();

        // then
        assertThat(store.getStoreEnableStatus()).isEqualTo(StoreEnableStatus.DISABLED);
    }
}