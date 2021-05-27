package com.delivery.user.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "User")
@Entity
public class User {

  // 아이디(pk)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 회원 아이디(이메일)
  @Column
  private String email;

  // 비밀번호
  @Column
  private String password;

  // 이름
  @Column
  private String name;

  // 휴대폰 번호
  @Column
  private String phoneNumber;

  // 회원 상태 DEFAULT(기본), DELETED(삭제됨)
  @Column
  @Enumerated(EnumType.STRING)
  private DataStatus status;

  // 회원가입일
  @Column
  private LocalDateTime createdAt;

  // 최종 수정일
  @Column
  private LocalDateTime updatedAt;

  public User() {
  }

  @Builder
  public User(Long id, String email, String password, String name, String phoneNumber,
      DataStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

}
