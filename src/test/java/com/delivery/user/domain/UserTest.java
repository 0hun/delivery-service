package com.delivery.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.delivery.user.dto.UserDto;
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
        .status(DataStatus.DEFAULT)
        .build();

    //when
    User user = userDto.toEntity();

    //then
    assertThat(user.getEmail()).isEqualTo(userDto.getEmail());
  }

}
