package com.delivery.store.model.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StoreRequestDtoTest {

    private static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @DisplayName("StoreRequestDto 기본생성자")
    @Test
    void initStore() {
        StoreRequestDto storeRequestDto = StoreRequestDto.builder()
            .build();

        assertThat(storeRequestDto.getName()).isNull();
    }

    @DisplayName("StoreRequestDto 매핑")
    @Test
    void mapping() {
        // given
        StoreRequestDto store = StoreRequestDto.builder()
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 27")
            .managerName("황윤호")
            .businessNumber("123-33-12345")
            .build();

        // when
        String name = store.getName();
        String telephone = store.getTelephone();
        String address = store.getAddress();
        String managerName = store.getManagerName();
        String businessNumber = store.getBusinessNumber();

        // then
        Assertions.assertAll(
            () -> assertThat(name).isEqualTo("곱돌이네"),
            () -> assertThat(telephone).isEqualTo("02-1234-5678"),
            () -> assertThat(address).isEqualTo("서울 송파구 송파1로 27"),
            () -> assertThat(managerName).isEqualTo("황윤호"),
            () -> assertThat(businessNumber).isEqualTo("123-33-12345")
        );
    }

    @DisplayName("StoreDto validator 실패")
    @Test
    void validateStore() {
        //given
        StoreRequestDto store = StoreRequestDto.builder()
            .name("곱돌이네")
            .telephone("02-1234-5678")
            .address("서울 송파구 송파1로 27")
            .managerName("황윤호")
            .businessNumber("123-32-13456")
            .build();

        //when
        Set<ConstraintViolation<StoreRequestDto>> violations = validator.validate(store);

        // then
        assertThat(violations).isEmpty();
    }


    @DisplayName("StoreDto validator 실패")
    @Test
    void validateStoreFailure() {
        //given
        StoreRequestDto store = StoreRequestDto.builder()
            .name("곱돌이네?")
            .telephone("02-12345678")
            .address("서울 송파구 송파1로 27*^")
            .managerName("hwang")
            .businessNumber("123-32613456")
            .build();

        //when
        Set<ConstraintViolation<StoreRequestDto>> violations = validator.validate(store);

        // then
        assertThat(violations).isNotEmpty();
    }
}