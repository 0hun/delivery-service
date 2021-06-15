package com.delivery.user.dto;

import com.delivery.user.domain.DataStatus;
import com.delivery.user.domain.User;
import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

// 유저 생성시 유효성 검사를 위한 class
@Getter
public class UserDto {

    private long id;

    // 회원 아이디(이메일)
    @NotBlank
    @Email
    @Length(max = 255)
    private String email;

    // 패스워드
    @NotBlank
    @Length(min = 8, max = 16)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{8,16}$")
    private String password;

    // 이름
    @NotBlank
    @Length(min = 3, max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
    private String name;

    // 핸드폰 번호
    @Pattern(regexp = "^(01[1|6|7|8|9|0])-(\\d{3,4})-(\\d{4})$")
    private String phoneNumber;

    // 상태 DEFAULT(기본), DELETED(삭제됨)
    private DataStatus status;

    public UserDto() {
    }

    @Builder
    public UserDto(long id, String email, String password, String name, String phoneNumber,
        DataStatus status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public User toEntity() {
        return User.builder()
            .email(this.email)
            .name(this.name)
            .phoneNumber(this.phoneNumber)
            .status(DataStatus.DEFAULT)
            .build();
    }

    public static UserDto of(User user) {
        return UserDto.builder()
            .id(user.getId())
            .email(user.getEmail())
            .name(user.getName())
            .phoneNumber(user.getPhoneNumber())
            .status(user.getStatus())
            .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDto userDto = (UserDto) o;
        return id == userDto.id && Objects.equals(email, userDto.email) && Objects
            .equals(password, userDto.password) && Objects.equals(name, userDto.name)
            && Objects.equals(phoneNumber, userDto.phoneNumber) && status == userDto.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, name, phoneNumber, status);
    }
}
