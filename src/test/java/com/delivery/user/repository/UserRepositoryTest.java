package com.delivery.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.delivery.user.domain.DataStatus;
import com.delivery.user.domain.User;
import com.delivery.user.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @DisplayName("user 객체를 저장 테스트")
  @Test
  void saveUser() {
    //given
    UserDto userDto = UserDto.builder()
        .email("whdudgns2654@naver.com")
        .name("조영훈")
        .password("asdqwe1234567!@#")
        .phoneNumber("010-1234-1234")
        .status(DataStatus.DEFAULT)
        .build();

    //when
    User user = userRepository.save(userDto.toEntity());

    //then
    assertThat(user.getEmail()).isEqualTo(userDto.getEmail());
  }

  @DisplayName("비어 있는 user 객체를 저장 테스트")
  @Test
  void saveEmptyUser() {
    //given
    User user = new User();

    //when
    User savedUser = userRepository.save(user);

    //then
    assertThat(savedUser.getEmail()).isNull();
  }

}
