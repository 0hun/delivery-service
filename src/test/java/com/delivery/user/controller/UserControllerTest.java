package com.delivery.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.delivery.common.exception.ExceptionController;
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
        mockMvc = MockMvcBuilders
            .standaloneSetup(userController)
            .setControllerAdvice(new ExceptionController())
            .build();
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

        given(userService.add(any())).willReturn(user);

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

        given(userService.existsByEmail(userDto.getEmail())).willReturn(true);

        // when
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

        given(userService.find(user.getId())).willReturn(user);

        // when
        // then
        mockMvc.perform(get("/users/" + user.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(new Gson().toJson(user)))
            .andDo(print());
    }

    @DisplayName("회원 조회 실패 테스트")
    @Test
    void findUserFail() throws Exception {
        // given
        long userId = 1L;

        given(userService.find(userId)).willThrow(NoSuchElementException.class);

        // when
        mockMvc.perform(get("/users/" + userId)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andDo(print());
    }

    @DisplayName("회원 삭제 성공 테스트")
    @Test
    void deleteUser() throws Exception {
        // given
        long userId = 1L;

        willDoNothing().given(userService).delete(userId);

        // when
        // then
        mockMvc.perform(delete("/users/" + userId)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    @DisplayName("회원 삭제 실패 테스트")
    @Test
    void deleteUserFail() throws Exception {
        // given
        long userId = 1L;

        willThrow(NoSuchElementException.class).given(userService).delete(userId);

        // when
        // then
        mockMvc.perform(delete("/users/" + userId)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andDo(print());
    }

    @DisplayName("회원 수정 성공 테스트")
    @Test
    void update() throws Exception {
        // given
        UserDto updateUserDto = UserDto.builder()
            .email("whdudgns2654@naver.com")
            .name("조영훈11")
            .password("asdqwe1234568!@#")
            .phoneNumber("010-1234-1235")
            .status(DataStatus.DEFAULT)
            .build();

        willDoNothing().given(userService).update(any());

        // when
        // then
        mockMvc.perform(patch("/users")
            .content(new Gson().toJson(updateUserDto))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    @DisplayName("회원 수정 실패 테스트")
    @Test
    void updateUserFail() throws Exception {
        // given
        UserDto updateUserDto = UserDto.builder()
            .email("whdudgns2654@naver.com")
            .name("조영훈11")
            .password("asdqwe1234568!@#")
            .phoneNumber("010-1234-1235")
            .status(DataStatus.DEFAULT)
            .build();

        willThrow(NoSuchElementException.class).given(userService).update(any());

        // when
        // then
        mockMvc.perform(patch("/users")
                .content(new Gson().toJson(updateUserDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}
