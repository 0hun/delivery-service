package com.delivery.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.delivery.user.domain.DataStatus;
import com.delivery.user.domain.User;
import com.delivery.user.dto.UserDto;
import com.delivery.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @InjectMocks
  private UserService userService;

  @Mock
  private UserRepository userRepository;

  @DisplayName("user 객체를 저장 테스트")
  @Test
  void addUser() {
    //given
    long userId = 1L;

    UserDto userDto = UserDto.builder()
        .email("whdudgns2654@naver.com")
        .name("조영훈")
        .password("asdqwe1234567!@#")
        .phoneNumber("010-1234-1234")
        .status(DataStatus.DEFAULT)
        .build();

    User mockUser = User.builder()
        .id(1L)
        .email("whdudgns2654@naver.com")
        .name("조영훈")
        .password("asdqwe1234567!@#")
        .phoneNumber("010-1234-1234")
        .status(DataStatus.DEFAULT)
        .build();

    doReturn(null).when(userRepository).save(any());

    doReturn(Optional.of(mockUser)).when(userRepository).findById(userId);

    //when
    userService.addUser(userDto);

    Optional<User> savedUser = userService.findById(userId);

    //then
    assertThat(savedUser.get().getId()).isEqualTo(userId);
  }

}
