package com.delivery.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.delivery.config.TestJpaConfig;
import com.delivery.user.domain.DataStatus;
import com.delivery.user.domain.User;
import com.delivery.user.dto.UserDto;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestJpaConfig.class)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @DisplayName("user 객체 저장 테스트")
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
        userRepository.save(userDto.toEntity());

        User savedUser = userRepository.findByEmail(userDto.getEmail()).get();

        //then
        assertThat(savedUser.getEmail()).isEqualTo(userDto.getEmail());
    }

    @DisplayName("비어 있는 user 객체 저장 테스트")
    @Test
    void saveEmptyUser() {
        //given
        User user = new User();

        //when
        User savedUser = userRepository.save(user);

        //then
        assertThat(savedUser.getEmail()).isNull();
    }

    @DisplayName("user 조회 테스트")
    @Test
    void findUser() {
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

        User savedUser = userRepository.findByEmail(userDto.getEmail()).get();

        //then
        assertThat(savedUser).isEqualTo(user);
    }

    @DisplayName("user 조회 실패 테스트")
    @Test
    void findUserFail() {
        //given
        long userId = 1L;

        //when
        Optional<User> savedUser = userRepository.findById(userId);

        //then
        assertThat(savedUser).isEqualTo(Optional.empty());
    }

    @DisplayName("user 존재 여부 테스트")
    @Test
    void existsUser() {
        //given
        UserDto userDto = UserDto.builder()
            .email("whdudgns2654@naver.com")
            .name("조영훈")
            .password("asdqwe1234567!@#")
            .phoneNumber("010-1234-1234")
            .status(DataStatus.DEFAULT)
            .build();

        //when
        userRepository.save(userDto.toEntity());

        boolean existsUser = userRepository.existsByEmail(userDto.getEmail());

        //then
        assertThat(existsUser).isEqualTo(true);
    }

    @DisplayName("user 존재 여부 실패 테스트")
    @Test
    void existsUserFail() {
        //given
        String email = "whdudgns2654@naver.com";

        //when
        boolean existsUser = userRepository.existsByEmail(email);

        //then
        assertThat(existsUser).isEqualTo(false);
    }

    @DisplayName("user 삭제 테스트")
    @Test
    void deleteUser() {
        //given
        UserDto userDto = UserDto.builder()
            .email("whdudgns2654@naver.com")
            .name("조영훈")
            .password("asdqwe1234567!@#")
            .phoneNumber("010-1234-1234")
            .status(DataStatus.DEFAULT)
            .build();

        userRepository.save(userDto.toEntity());

        //when
        User savedUser = userRepository.findByEmail(userDto.getEmail()).get();

        savedUser.delete();

        //then
        assertThat(savedUser.getStatus()).isEqualTo(DataStatus.DELETED);
    }

    @DisplayName("user 삭제 실패 테스트")
    @Test
    void deleteUserFail() {
        //given
        long userId = 1L;

        //when
        Optional<User> savedUser = userRepository.findById(userId);

        //then
        assertThat(savedUser).isEqualTo(Optional.empty());
    }

    @DisplayName("user 객체 수정 테스트")
    @Test
    void updateUser() {
        //given
        UserDto userDto = UserDto.builder()
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

        //when
        userRepository.save(userDto.toEntity());

        User savedUser = userRepository.findByEmail(userDto.getEmail()).get();

        savedUser.update(updateUserDto);

        //then
        assertThat(savedUser.getName()).isEqualTo(updateUserDto.getName());
    }

}
