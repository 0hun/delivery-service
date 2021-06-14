package com.delivery.user.domain;

import com.delivery.common.entity.BaseTimeEntity;
import com.delivery.user.dto.UserDto;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @NoArgsConstructor(access = AccessLevel.PROTECTED) : jpa는 프록시를 통해서 entity 객체를 런타임에 동적으로 생성하는데,
 * 이때 접근 권한이 PROTECTED이면 된다. 또한 기본 생성자를 열어둘 경우 불완전한 entity 객체가 생성될 수 있으므로 기본 생성자의 접근을 제한한다.
 */

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

  // 아이디(pk)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 회원 아이디(이메일)
  @Column(name = "email", nullable = false)
  private String email;

  // 비밀번호
  @Column(name = "password", length = 16, nullable = false)
  private String password;

  // 이름
  @Column(name = "name", length = 6, nullable = false)
  private String name;

  // 휴대폰 번호
  @Column(name = "phoneNumber", length = 12)
  private String phoneNumber;

  // 회원 상태 DEFAULT(기본), DELETED(삭제됨)
  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private DataStatus status;

  @Builder
  public User(Long id, String email, String password, String name, String phoneNumber,
      DataStatus status) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.status = status;
  }

  public void delete() {
    this.status = DataStatus.DELETED;
  }

  public void update(UserDto updateUserDto) {
    this.password = updateUserDto.getPassword();
    this.name = updateUserDto.getName();
    this.phoneNumber = updateUserDto.getPhoneNumber();
    this.status = updateUserDto.getStatus();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(id, user.id) && Objects.equals(email, user.email)
        && Objects.equals(password, user.password) && Objects
        .equals(name, user.name) && Objects.equals(phoneNumber, user.phoneNumber)
        && status == user.status;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, password, name, phoneNumber, status);
  }
}
