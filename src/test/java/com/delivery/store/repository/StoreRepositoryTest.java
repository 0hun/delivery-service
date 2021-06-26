package com.delivery.store.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.delivery.store.domain.StoreEnableStatus;
import com.delivery.store.domain.Store;
import com.delivery.store.dto.request.StoreRequestDto;
import com.delivery.user.domain.DataStatus;
import com.delivery.user.domain.User;
import com.delivery.user.dto.UserDto;
import com.delivery.user.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StoreRepositoryTest {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    StoreRequestDto storeRequestDto;

    @BeforeEach
    void initData() {
        User user = userRepository.save(UserDto.builder()
            .email("whdudgns2654@naver.com")
            .name("조영훈")
            .password("asdqwe1234567!@#")
            .phoneNumber("010-1234-1234")
            .status(DataStatus.DEFAULT)
            .build()
            .toEntity());

        storeRequestDto = StoreRequestDto.builder()
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 4427")
            .managerName("황윤호")
            .businessNumber("123-33-12345")
            .userId(user.getId())
            .build();
        storeRepository.save(storeRequestDto.toEntity());
    }

    /**
     * Class 전체 테스트시 auto-increment 를 초기화 해줘야 한다
     */
    @AfterEach
    void resetAutoincrement() {
        storeRepository.deleteAll();
        this.entityManager
            .createNativeQuery("ALTER TABLE store ALTER COLUMN `store_id` RESTART WITH 1")
            .executeUpdate();
    }

    @DisplayName("Store 조회 테스트")
    @Test
    void find() {
        // given

        // when
        Store store = storeRepository.findById(1L)
            .orElseThrow(() -> new NoSuchElementException("해당 ID는 존재하지 않습니다."));

        // then
        assertThat(store.getName()).isEqualTo(storeRequestDto.getName());
    }

    @DisplayName("Store 조회 결과 없음")
    @Test
    void findFailure() {
        // given

        // when, then
        assertThatThrownBy(() -> {
            storeRepository.findById(2L)
                .orElseThrow(() -> new NoSuchElementException("해당 ID는 존재하지 않습니다."));
        }).isInstanceOf(NoSuchElementException.class)
            .hasMessage("해당 ID는 존재하지 않습니다.");
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
            .address("서울 송파구 송파1로 4427")
            .managerName("황윤호")
            .businessNumber("123-33-12345")
            .build();
        Store store = storeRepository.findById(1L)
            .orElseThrow(() -> new NoSuchElementException("해당 ID는 존재하지 않습니다."));

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
            .orElseThrow(() -> new NoSuchElementException("해당 ID는 존재하지 않습니다."));

        // when
        store.disableStore();

        // then
        assertThat(store.getStoreEnableStatus()).isEqualTo(StoreEnableStatus.DISABLED);
    }
}