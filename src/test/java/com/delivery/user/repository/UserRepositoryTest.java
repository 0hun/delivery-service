package com.delivery.user.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.delivery.common.config.JpaAuditingConfig;
import com.delivery.user.domain.DataStatus;
import com.delivery.user.domain.User;
import com.delivery.user.domain.UserRole;
import com.delivery.user.dto.UserDto;
import java.util.Optional;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@DataJpaTest(includeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE,
    classes = JpaAuditingConfig.class
))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @DisplayName("user 객체 저장 테스트")
    @Test
    void saveUser() {
        //given
        String password = "asdqwe123456!@#";

        String encodedPassword = passwordEncoder.encode(password);

        UserDto userDto = UserDto.builder()
            .email("whdudgns2654@naver.com")
            .name("조영훈")
            .password(encodedPassword)
            .phoneNumber("010-1234-1234")
            .role(UserRole.ROLE_USER)
            .status(DataStatus.DEFAULT)
            .build();

        //when
        userRepository.save(userDto.toEntity());

        User savedUser = userRepository.findByEmail(userDto.getEmail()).get();

        //then
        assertThat(savedUser.getEmail()).isEqualTo(userDto.getEmail());
    }

    @DisplayName("값이 일부 비어 있는 user 객체 저장 테스트 - 객체 insert시 제약조건 위반 테스트")
    @Test
    void saveEmptyUser() {
        //given
        String password = "asdqwe1234567!@#";

        String encodedPassword = passwordEncoder.encode(password);

        UserDto userDto = UserDto.builder()
            .name("조영훈")
            .password(encodedPassword)
            .phoneNumber("010-1234-1234")
            .role(UserRole.ROLE_USER)
            .status(DataStatus.DEFAULT)
            .build();

        //when
        Throwable thrown = catchThrowable(() -> {
            userRepository.save(userDto.toEntity());
        });


        //then
        assertThat(thrown.getCause()).isInstanceOf(ConstraintViolationException.class);
    }

    @DisplayName("user 조회 테스트")
    @Test
    void findUser() {
        //given
        String password = "asdqwe1234567!@#";

        String encodedPassword = passwordEncoder.encode(password);

        UserDto userDto = UserDto.builder()
            .email("whdudgns2654@naver.com")
            .name("조영훈")
            .password(encodedPassword)
            .phoneNumber("010-1234-1234")
            .role(UserRole.ROLE_USER)
            .status(DataStatus.DEFAULT)
            .build();

        //when
        User user = userDto.toEntity();

        userRepository.save(user);

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
        String password = "asdqwe1234567!@#";

        String encodedPassword = passwordEncoder.encode(password);

        UserDto userDto = UserDto.builder()
            .email("whdudgns2654@naver.com")
            .name("조영훈")
            .password(encodedPassword)
            .phoneNumber("010-1234-1234")
            .role(UserRole.ROLE_USER)
            .status(DataStatus.DEFAULT)
            .build();

        //when
        User user = userDto.toEntity();

        userRepository.save(user);

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
        String password = "asdqwe1234567!@#";
        String encodedPassword = passwordEncoder.encode(password);

        UserDto userDto = UserDto.builder()
            .email("whdudgns2654@naver.com")
            .name("조영훈")
            .password(encodedPassword)
            .phoneNumber("010-1234-1234")
            .role(UserRole.ROLE_USER)
            .status(DataStatus.DEFAULT)
            .build();

        User user = userDto.toEntity();

        userRepository.save(user);

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
        String password = "asdqwe1234567!@#";

        String encodedPassword = passwordEncoder.encode(password);

        UserDto userDto = UserDto.builder()
            .email("whdudgns2654@naver.com")
            .name("조영훈")
            .password(encodedPassword)
            .phoneNumber("010-1234-1234")
            .role(UserRole.ROLE_USER)
            .status(DataStatus.DEFAULT)
            .build();

        UserDto updateUserDto = UserDto.builder()
            .name("조영훈11")
            .phoneNumber("010-1234-1235")
            .build();

        //when
        User user = userDto.toEntity();

        userRepository.save(user);

        User savedUser = userRepository.findByEmail(userDto.getEmail()).get();

        savedUser.update(updateUserDto);

        //then
        assertThat(savedUser.getName()).isEqualTo(updateUserDto.getName());
    }

    @DisplayName("user 비밀번호 수정 테스트")
    @Test
    void userChangePassword() {
        //given
        String password = "asdqwe1234567!@#";

        String encodedPassword = passwordEncoder.encode(password);

        String newPassword = "asdqwe7234567!@#";

        String encodedNewPassword = passwordEncoder.encode(newPassword);

        UserDto userDto = UserDto.builder()
            .email("whdudgns2654@naver.com")
            .name("조영훈")
            .password(encodedPassword)
            .phoneNumber("010-1234-1234")
            .role(UserRole.ROLE_USER)
            .status(DataStatus.DEFAULT)
            .build();

        //when
        User user = userDto.toEntity();

        userRepository.save(user);

        User savedUser = userRepository.findByEmail(userDto.getEmail()).get();

        savedUser.changePassword(encodedNewPassword);

        //then
        assertThat(passwordEncoder.matches(newPassword, encodedNewPassword)).isTrue();
    }

    @Test
    void saveUserAutoAuditedTest() {
        //given
        String password = "asdqwe123456!@#";

        String encodedPassword = passwordEncoder.encode(password);

        UserDto userDto = UserDto.builder()
            .email("whdudgns2654@naver.com")
            .name("조영훈")
            .password(encodedPassword)
            .phoneNumber("010-1234-1234")
            .role(UserRole.ROLE_USER)
            .status(DataStatus.DEFAULT)
            .build();

        //when
        User savedUser = userRepository.save(userDto.toEntity());

        assertAll(
            () -> assertNotNull(savedUser.getCreatedAt()),
            () -> assertNotNull(savedUser.getUpdatedAt())
        );
    }

}
