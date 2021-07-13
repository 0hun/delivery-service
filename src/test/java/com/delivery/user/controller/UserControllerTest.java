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

import com.delivery.user.domain.DataStatus;
import com.delivery.user.domain.User;
import com.delivery.user.dto.UserChangePasswordDto;
import com.delivery.user.dto.UserDto;
import com.delivery.user.service.UserService;
import com.google.gson.Gson;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("회원 가입 성공 테스트")
    @Test
    @WithMockUser
    void addUser() throws Exception {
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
    @WithMockUser
    void addUserFail() throws Exception {
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
    @WithMockUser
    void addUserWithExistsUser() throws Exception {
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
    @WithMockUser
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

        UserDto userDto = UserDto.of(user);

        given(userService.find(user.getId())).willReturn(userDto);

        // when
        // then
        mockMvc.perform(get("/users/" + user.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(new Gson().toJson(userDto)))
            .andDo(print());
    }

    @DisplayName("회원 조회 실패 테스트")
    @Test
    @WithMockUser
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
    @WithMockUser
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
    @WithMockUser
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
    @WithMockUser
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
    @WithMockUser
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

    @DisplayName("회원 비밀번호 수정 성공 테스트")
    @Test
    @WithMockUser
    void changePassword() throws Exception {
        // given
        UserChangePasswordDto dto = UserChangePasswordDto.builder()
            .email("whdudgns2654@naver.com")
            .password("asdqwe1234568!@#")
            .newPassword("asdqwe1234577!@#")
            .build();

        willDoNothing().given(userService).changePassword(any());

        // when
        // then
        mockMvc.perform(patch("/users/password")
            .content(new Gson().toJson(dto))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(print());
    }


    @DisplayName("회원 비밀번호 수정 실패 테스트")
    @Test
    @WithMockUser
    void changePasswordFail() throws Exception {
        // given
        UserChangePasswordDto dto = UserChangePasswordDto.builder()
            .email("whdudgns2654@naver.com")
            .password("asdqwe1234568!@#")
            .newPassword("asdqwe1234577!@#")
            .build();

        willThrow(IllegalArgumentException.class).given(userService).changePassword(any());

        // when
        // then
        mockMvc.perform(patch("/users/password")
            .content(new Gson().toJson(dto))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

}
