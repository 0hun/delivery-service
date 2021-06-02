package com.delivery.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.delivery.user.domain.DataStatus;
import com.delivery.user.domain.User;
import com.delivery.user.dto.UserDto;
import com.delivery.user.repository.UserRepository;
import java.util.NoSuchElementException;
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

  @DisplayName("user 조회 테스트")
  @Test
  void findUser() {
    //given
    User mockUser = User.builder()
        .id(1L)
        .email("whdudgns2654@naver.com")
        .name("조영훈")
        .password("asdqwe1234567!@#")
        .phoneNumber("010-1234-1234")
        .status(DataStatus.DEFAULT)
        .build();


    doReturn(Optional.of(mockUser)).when(userRepository).findById(mockUser.getId());

    //when
    User savedUser = userService.findById(mockUser.getId());

    //then
    assertThat(savedUser).isEqualTo(mockUser);
  }

  @DisplayName("user 조회 실패 테스트")
  @Test
  void findUserFail() {
    //given
   long userId = 1L;

    doReturn(Optional.empty()).when(userRepository).findById(userId);

    //when
    Throwable thrown = catchThrowable(() -> userService.findById(userId));

    //then
    assertThat(thrown).isInstanceOf(NoSuchElementException.class);
  }

  @DisplayName("user 저장 테스트")
  @Test
  void addUser() {
    //given
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

    doReturn(mockUser).when(userRepository).save(any());

    //when
    User savedUser = userService.addUser(userDto);

    //then
    assertThat(savedUser).isEqualTo(mockUser);
  }

  @DisplayName("user 존재 여부 테스트")
  @Test
  void existsUser() {
    //given
    String email = "whdudgns2654@naver.com";

    doReturn(true).when(userRepository).existsByEmail(email);

    //when
    boolean existsUser = userService.existsByEmail(email);

    //then
    assertThat(existsUser).isEqualTo(true);
  }

  @DisplayName("user 존재 여부 실패 테스트")
  @Test
  void existsUserFail() {
    //given
    String email = "whdudgns2654@naver.com";

    doReturn(false).when(userRepository).existsByEmail(email);

    //when
    boolean existsUser = userService.existsByEmail(email);

    //then
    assertThat(existsUser).isEqualTo(false);
  }

  @DisplayName("user 삭제 테스트")
  @Test
  void deleteUser() {
    //given
    User mockUser = User.builder()
        .id(1L)
        .email("whdudgns2654@naver.com")
        .name("조영훈")
        .password("asdqwe1234567!@#")
        .phoneNumber("010-1234-1234")
        .status(DataStatus.DEFAULT)
        .build();

    doReturn(Optional.of(mockUser)).when(userRepository).findById(mockUser.getId());

    //when
    User deletedUser = userService.delete(1L);

    //then
    assertThat(deletedUser.getStatus()).isEqualTo(DataStatus.DELETED);
  }

  @DisplayName("user 삭제 실패 테스트")
  @Test
  void deleteUserFail() {
    //given
    long userId = 1L;

    doReturn(Optional.empty()).when(userRepository).findById(userId);

    //when
    Throwable thrown = catchThrowable(() -> userService.delete(userId));

    //then
    assertThat(thrown).isInstanceOf(NoSuchElementException.class);
  }

}
