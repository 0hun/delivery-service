package com.delivery.user.dto;

import com.delivery.user.domain.DataStatus;
import com.delivery.user.domain.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

// 유저 생성시 유효성 검사를 위한 class
@Getter
public class UserDto {

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
  public UserDto(String email, String password, String name, String phoneNumber,
      DataStatus status) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.status = status;
  }

  public User toEntity() {
    return User.builder()
        .email(this.email)
        .password(this.password)
        .name(this.name)
        .phoneNumber(this.phoneNumber)
        .status(DataStatus.DEFAULT)
        .build();
  }

}
