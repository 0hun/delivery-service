package com.delivery.store.model.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.delivery.store.model.StoreEnableStatus;
import com.delivery.store.model.request.StoreRequestDto;

class StoreTest {

    @DisplayName("Store 기본생성자")
    @Test
    void store_default_생성자() {
        Store storeEntity = new Store();

        assertThat(storeEntity.getName()).isEqualTo(null);
    }

    @DisplayName("Store 매핑")
    @Test
    void store_엔티티_매핑() {
        // given
        Store store = Store.builder()
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 27")
            .managerName("황윤호")
            .businessNumber("123123933")
            .storeEnableStatus(StoreEnableStatus.ENABLED)
            .build();

        // when
        String name = store.getName();
        String telephone = store.getTelephone();
        String address = store.getAddress();
        String managerName = store.getManagerName();
        String businessNumber = store.getBusinessNumber();
        StoreEnableStatus storeEnableStatus = store.getStoreEnableStatus();

        // then
        Assertions.assertAll(
            () -> assertThat(name).isEqualTo("곱돌이네"),
            () -> assertThat(telephone).isEqualTo("02-1234-5678"),
            () -> assertThat(address).isEqualTo("서울 송파구 송파1로 27"),
            () -> assertThat(managerName).isEqualTo("황윤호"),
            () -> assertThat(businessNumber).isEqualTo("123123933"),
            () -> assertThat(storeEnableStatus).isEqualTo(StoreEnableStatus.ENABLED)
        );
    }

    @DisplayName("Store 업데이트")
    @Test
    void store_엔티티_수정() {
        // given
        Store store = Store.builder()
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 27")
            .managerName("황윤호")
            .businessNumber("123123933")
            .build();

        StoreRequestDto storeRequestDto = StoreRequestDto.builder()
            .name("윤호네")
            .telephone("010-1234-5678")
            .address("서울 송파구 송파1로 52")
            .managerName("mane")
            .businessNumber("123123933")
            .build();

        // when
        store.updateInformation(storeRequestDto);

        // then
        Assertions.assertAll(
            () -> assertThat(store.getName()).isEqualTo(storeRequestDto.getName()),
            () -> assertThat(store.getTelephone()).isEqualTo(storeRequestDto.getTelephone()),
            () -> assertThat(store.getAddress()).isEqualTo(storeRequestDto.getAddress()),
            () -> assertThat(store.getManagerName()).isEqualTo(storeRequestDto.getManagerName()),
            () -> assertThat(store.getBusinessNumber()).isEqualTo(storeRequestDto.getBusinessNumber())
        );
    }

    @DisplayName("Store 비활성화")
    @Test
    void store_엔티티_비활성화() {
        // given
        Store store = Store.builder()
            .name("곱돌이네")
            .storeEnableStatus(StoreEnableStatus.ENABLED)
            .build();

        // when
        store.disableStore();

        // then
        assertThat(store.getStoreEnableStatus()).isEqualTo(StoreEnableStatus.DISABLED);
    }
}
