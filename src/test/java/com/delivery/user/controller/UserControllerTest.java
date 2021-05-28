package com.delivery.user.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.delivery.user.domain.DataStatus;
import com.delivery.user.dto.UserDto;
import com.delivery.user.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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

    doNothing().when(userService).addUser(any());

    // when
    final ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new Gson().toJson(userDto)));

    // then
    assertThat(resultActions.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
  }

}
