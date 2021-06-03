package com.delivery.user.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.delivery.user.domain.DataStatus;
import com.delivery.user.domain.User;
import com.delivery.user.dto.UserDto;
import com.delivery.user.service.UserService;
import com.google.gson.Gson;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  @InjectMocks
  private UserController userController;

  @Mock
  private UserService userService;

  private MockMvc mockMvc;

  @BeforeEach
  public void init() {
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
  }

  @DisplayName("회원 가입 성공 테스트")
  @Test
  void signUp() throws Exception {
    // given
    UserDto userDto = UserDto.builder()
        .email("whdudgns2654@naver.com")
        .name("조영훈")
        .password("asdqwe1234567!@#")
        .phoneNumber("010-1234-1234")
        .status(DataStatus.DEFAULT)
        .build();

    User user = User.builder()
        .id(1L)
        .email("whdudgns2654@naver.com")
        .name("조영훈")
        .password("asdqwe1234567!@#")
        .phoneNumber("010-1234-1234")
        .status(DataStatus.DEFAULT)
        .build();

    doReturn(user).when(userService).add(any());

    // when
    // then
    mockMvc.perform(post("/users")
        .content(new Gson().toJson(userDto))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(redirectedUrl("/users/1"))
        .andExpect(status().isCreated())
        .andDo(print());
  }

  @DisplayName("회원 가입 실패 테스트 - 값이 없는 빈 객체 전달하여 회원 가입 실패 테스트")
  @Test
  void signUpFail() throws Exception {
    // given
    UserDto userDto = new UserDto();

    // when
    // then
    mockMvc.perform(post("/users")
        .content(new Gson().toJson(userDto))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @DisplayName("회원 가입 실패 테스트 - 같은 이메일을 가진 유저가 존재하여 실패 테스트")
  @Test
  void signUpFailWithExistsUser() throws Exception {
    // given
    UserDto userDto = UserDto.builder()
        .email("whdudgns2654@naver.com")
        .name("조영훈")
        .password("asdqwe1234567!@#")
        .phoneNumber("010-1234-1234")
        .status(DataStatus.DEFAULT)
        .build();

    String email = "whdudgns2654@naver.com";

    // when
    doReturn(true).when(userService).existsByEmail(email);

    // then
    mockMvc.perform(post("/users")
        .content(new Gson().toJson(userDto))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
        .andDo(print());
  }

  @DisplayName("회원 조회 성공 테스트")
  @Test
  void findUser() throws Exception {
    // given
    User user = User.builder()
        .id(1L)
        .email("whdudgns2654@naver.com")
        .name("조영훈")
        .password("asdqwe1234567!@#")
        .phoneNumber("010-1234-1234")
        .status(DataStatus.DEFAULT)
        .build();

    doReturn(user).when(userService).find(1L);

    // when
    // then
    mockMvc.perform(get("/users/" + 1L)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(new Gson().toJson(user)))
        .andDo(print());
  }

  @DisplayName("회원 조회 실패 테스트")
  @Test
  void findUserFail() {
    // given
    long userId = 1L;

    // when
    doThrow(NoSuchElementException.class).when(userService).find(userId);

    // then
    Throwable thrown = catchThrowable(() -> mockMvc.perform(get("/users/" + userId)
        .accept(MediaType.APPLICATION_JSON)));

    assertThat(thrown.getCause()).isInstanceOf(NoSuchElementException.class);
  }

  @DisplayName("회원 삭제 성공 테스트")
  @Test
  void deleteUser() throws Exception {
    // given
    User user = User.builder()
        .id(1L)
        .email("whdudgns2654@naver.com")
        .name("조영훈")
        .password("asdqwe1234567!@#")
        .phoneNumber("010-1234-1234")
        .status(DataStatus.DEFAULT)
        .build();

    doNothing().when(userService).delete(1L);

    // when
    // then
    mockMvc.perform(delete("/users/" + 1L)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent())
        .andDo(print());
  }

  @DisplayName("회원 삭제 실패 테스트")
  @Test
  void deleteUserFail() {
    // given
    long userId = 1L;

    // when
    doThrow(NoSuchElementException.class).when(userService).delete(userId);

    // then
    Throwable thrown = catchThrowable(() -> mockMvc.perform(delete("/users/" + userId)
        .accept(MediaType.APPLICATION_JSON)));

    assertThat(thrown.getCause()).isInstanceOf(NoSuchElementException.class);
  }

}
