package com.delivery.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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

        UserDto mockUserUserDto = UserDto.of(mockUser);

        given(userRepository.findById(mockUser.getId())).willReturn(Optional.of(mockUser));

        //when
        UserDto userDto = userService.find(mockUser.getId());

        //then
        assertThat(userDto).isEqualTo(mockUserUserDto);
    }

    @DisplayName("user 조회 실패 테스트")
    @Test
    void findUserFail() {
        //given
        String email = "whdudgns2654@naver.com";

        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        //when
        Throwable thrown = catchThrowable(() -> userService.find(email));

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

        given(userRepository.save(any())).willReturn(mockUser);

        //when
        User savedUser = userService.add(userDto);

        //then
        assertThat(savedUser).isEqualTo(mockUser);
    }

    @DisplayName("user 존재 여부 테스트")
    @Test
    void existsUser() {
        //given
        String email = "whdudgns2654@naver.com";

        given(userRepository.existsByEmail(email)).willReturn(true);

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

        given(userRepository.existsByEmail(email)).willReturn(false);

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

        given(userRepository.findById(mockUser.getId())).willReturn(Optional.of(mockUser));

        //when
        userService.delete(mockUser.getId());

        UserDto userDto = userService.find(mockUser.getId());

        //then
        assertThat(userDto.getStatus()).isEqualTo(DataStatus.DELETED);
    }

    @DisplayName("user 삭제 실패 테스트")
    @Test
    void deleteUserFail() {
        //given
        long userId = 1L;

        given(userRepository.findById(userId)).willReturn(Optional.empty());

        //when
        Throwable thrown = catchThrowable(() -> userService.delete(userId));

        //then
        assertThat(thrown).isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("user update 테스트")
    @Test
    void updateUser() {
        //given
        User mockUser = User.builder()
            .id(1L)
            .email("whdudgns2654@naver.com")
            .name("조영훈")
            .password("asdqwe1234567!@#")
            .phoneNumber("010-1234-1234")
            .status(DataStatus.DEFAULT)
            .build();

        UserDto updateUserDto = UserDto.builder()
            .email("whdudgns2654@naver.com")
            .name("조영훈11")
            .password("asdqwe1234568!@#")
            .phoneNumber("010-1234-1235")
            .status(DataStatus.DEFAULT)
            .build();

        given(userRepository.findByEmail(mockUser.getEmail())).willReturn(Optional.of(mockUser));

        //when
        userService.update(updateUserDto);

        UserDto userDto = userService.find(mockUser.getEmail());

        //then
        assertThat(userDto.getEmail()).isEqualTo(updateUserDto.getEmail());
    }

    @DisplayName("user 삭제 실패 테스트")
    @Test
    void updateUserFail() {
        //given
        UserDto updateUserDto = UserDto.builder()
            .email("whdudgns2654@naver.com")
            .name("조영훈11")
            .password("asdqwe1234568!@#")
            .phoneNumber("010-1234-1235")
            .status(DataStatus.DEFAULT)
            .build();

        given(userRepository.findByEmail(updateUserDto.getEmail())).willReturn(Optional.empty());

        //when
        Throwable thrown = catchThrowable(() -> userService.update(updateUserDto));

        //then
        assertThat(thrown).isInstanceOf(NoSuchElementException.class);
    }

}
