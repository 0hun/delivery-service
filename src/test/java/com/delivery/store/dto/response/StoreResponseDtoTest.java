package com.delivery.store.dto.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.delivery.store.domain.Store;
import com.delivery.user.domain.User;

class StoreResponseDtoTest {

    @DisplayName("StoreResponseDto 매핑")
    @Test
    void mapping() {
        // given
        Store store = Store.builder()
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 27")
            .managerName("황윤호")
            .businessNumber("123-33-12345")
            .user(User.builder().id(1L).build())
            .build();

        // when
        StoreResponseDto storeResponseDto = new StoreResponseDto(store);

        String name = storeResponseDto.getName();
        String telephone = storeResponseDto.getTelephone();
        String address = storeResponseDto.getAddress();
        String managerName = storeResponseDto.getManagerName();
        String businessNumber = storeResponseDto.getBusinessNumber();
        Long userId = storeResponseDto.getUserId();

        // then
        Assertions.assertAll(
            () -> assertThat(name).isEqualTo("곱돌이네"),
            () -> assertThat(telephone).isEqualTo("02-1234-5678"),
            () -> assertThat(address).isEqualTo("서울 송파구 송파1로 27"),
            () -> assertThat(managerName).isEqualTo("황윤호"),
            () -> assertThat(businessNumber).isEqualTo("123-33-12345"),
            () -> assertThat(userId).isEqualTo(1L)
        );
    }

    @DisplayName("StoreResponseDto localDateTimeToString 시간비교")
    @Test
    void convertTime() {
        // given
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String stringNowTime = LocalDateTime.now().format(dateTimeFormatter);
        Store store = Store.builder()
            .user(User.builder().id(1L).build())
            .build();

        // when
        StoreResponseDto storeResponseDto = new StoreResponseDto(store);
        String createdAt = storeResponseDto.getCreatedAt();
        String updatedAt = storeResponseDto.getUpdatedAt();

        // then
        Assertions.assertAll(
            () -> assertThat(createdAt).isGreaterThanOrEqualTo(stringNowTime),
            () -> assertThat(updatedAt).isGreaterThanOrEqualTo(stringNowTime)
        );
    }
}