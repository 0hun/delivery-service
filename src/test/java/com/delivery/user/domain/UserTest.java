package com.delivery.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.delivery.user.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

    @DisplayName("userDto를 이용하여 user 객체를 생성 테스트")
    @Test
    void createUser() {
        //given
        UserDto userDto = UserDto.builder()
            .email("whdudgns2654@naver.com")
            .name("조영훈")
            .password("asdqwe1234567!@#")
            .phoneNumber("010-1234-1234")
            .role(UserRole.ROLE_USER)
            .status(DataStatus.DEFAULT)
            .build();

        //when
        User user = userDto.toEntity();

        //then
        assertThat(user.getEmail()).isEqualTo(userDto.getEmail());
    }

    @DisplayName("User Entity 매핑")
    @Test
    void userEntityMapping() {
        //given
        UserDto userDto = UserDto.builder()
            .email("whdudgns2654@naver.com")
            .name("조영훈")
            .password("asdqwe1234567!@#")
            .phoneNumber("010-1234-1234")
            .status(DataStatus.DEFAULT)
            .role(UserRole.ROLE_USER)
            .build();

        //when
        String email = userDto.getEmail();
        String name = userDto.getName();
        String password = userDto.getPassword();
        String phoneNumber = userDto.getPhoneNumber();
        DataStatus status = userDto.getStatus();
        UserRole userRole = userDto.getRole();

        //then
        Assertions.assertAll(
            () -> assertThat(email).isEqualTo("whdudgns2654@naver.com"),
            () -> assertThat(name).isEqualTo("조영훈"),
            () -> assertThat(password).isEqualTo("asdqwe1234567!@#"),
            () -> assertThat(phoneNumber).isEqualTo("010-1234-1234"),
            () -> assertThat(status).isEqualTo(DataStatus.DEFAULT),
            () -> assertThat(userRole).isEqualTo(UserRole.ROLE_USER)
        );
    }

    @DisplayName("User delete 테스트")
    @Test
    void userDelete() {
        //given
        User mockUser = User.builder()
            .id(1L)
            .email("whdudgns2654@naver.com")
            .name("조영훈")
            .password("asdqwe1234567!@#")
            .phoneNumber("010-1234-1234")
            .status(DataStatus.DEFAULT)
            .role(UserRole.ROLE_USER)
            .build();

        //when
        mockUser.delete();

        //then
        assertThat(mockUser.getStatus()).isEqualTo(DataStatus.DELETED);
    }

}
