package com.delivery.user.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.delivery.user.domain.DataStatus;
import com.delivery.user.domain.User;
import com.delivery.common.domain.UserRole;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserDtoTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @DisplayName("userDto 객체를 생성 테스트")
    @Test
    void createUserDto() {
        //given
        String email = "whdudgns2654@naver.com";
        String name = "조영훈";
        String password = "asdqwe1234567!@#";
        String phoneNumber = "010-1234-1234";

        //when
        UserDto userDto = UserDto.builder()
            .email(email)
            .name(name)
            .password(password)
            .phoneNumber(phoneNumber)
            .role(UserRole.ROLE_USER)
            .status(DataStatus.DEFAULT)
            .build();

        //then
        assertThat(userDto.getEmail()).isEqualTo(email);
    }

    @DisplayName("userDto를 이용하여 user 객체를 생성 테스트")
    @Test
    void createUserEntity() {
        //given
        UserDto userDto = UserDto.builder()
            .email("whdudgns2654@naver.com")
            .name("조영훈")
            .password("asdqwe1234567!@#")
            .phoneNumber("010-1234-1234")
            .role(UserRole.ROLE_USER)
            .status(DataStatus.DEFAULT)
            .build();

        //when
        User user = userDto.toEntity();

        //then
        assertThat(user.getEmail()).isEqualTo(userDto.getEmail());
    }

    @DisplayName("userDto 객체 생성 시 validator 테스트")
    @Test
    void userDtoValidatorTest() {
        //given
        String email = "whdudgns2654@naver.com";
        String name = "조영훈";
        String password = "asdqwe1234567!@#";
        String phoneNumber = "010-1234-1234";

        //when
        UserDto userDto = UserDto.builder()
            .email(email)
            .name(name)
            .password(password)
            .phoneNumber(phoneNumber)
            .role(UserRole.ROLE_USER)
            .status(DataStatus.DEFAULT)
            .build();

        //then
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertThat(violations).isEmpty();
    }

    @DisplayName("userDto 객체 생성 시 validator 실패 테스트")
    @Test
    void userDtoValidatorFail() {
        //given
        String email = "whdudgns2654";
        String name = "조영";
        String password = "1234567";
        String phoneNumber = "010-1234-1234";

        //when
        UserDto userDto = UserDto.builder()
            .email(email)
            .name(name)
            .password(password)
            .phoneNumber(phoneNumber)
            .role(UserRole.ROLE_USER)
            .status(DataStatus.DEFAULT)
            .build();

        //then
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertThat(violations).isNotEmpty();
    }

    @DisplayName("userChangeDto 객체 생성 시 validator 성공 테스트")
    @Test
    void userChangePasswordDtoValidatorSuccess() {
        //given
        String email = "whdudgns2654@gmail.com";
        String password = "asdqwe1234567!@#";
        String newPassword = "asdqwe7234567!@#";

        //when
        UserChangePasswordDto userChangePasswordDto = UserChangePasswordDto.builder()
            .email(email)
            .password(password)
            .newPassword(newPassword)
            .build();

        //then
        Set<ConstraintViolation<UserChangePasswordDto>> violations = validator.validate(userChangePasswordDto);

        assertThat(violations).isEmpty();
    }

    @DisplayName("userChangeDto 객체 생성 시 validator 성공 테스트")
    @Test
    void userChangePasswordDtoValidatorFail() {
        //given
        String email = "whdudgns2654@gmail.com";
        String password = "1234567";
        String newPassword = "1234556777";

        //when
        UserChangePasswordDto userChangePasswordDto = UserChangePasswordDto.builder()
            .email(email)
            .password(password)
            .newPassword(newPassword)
            .build();

        //then
        Set<ConstraintViolation<UserChangePasswordDto>> violations = validator.validate(userChangePasswordDto);

        assertThat(violations).isNotEmpty();
    }

}
