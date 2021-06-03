package com.delivery.user.controller;

import com.delivery.user.domain.User;
import com.delivery.user.dto.UserDto;
import com.delivery.user.service.UserService;
import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor // 초기화 되지 않은 final field에 대해 생성자를 생성. final field에 의존성 주입
@RestController
public class UserController {

  private final UserService userService;

  /**
   * 회원 가입 메소드 회원 가입 성공시 201(Created) code return. 객체 valitaion 실패시 에러 정보와 400(Bad Request) code return.
   * @param userDto 저장할 회원의 정보
   * @return ResponseEntity(성공시 201 code, 실패시 400 code)
   */
  @PostMapping()
  public ResponseEntity<Void> signUp(@RequestBody @Valid UserDto userDto) throws URISyntaxException {
    User user = userService.addUser(userDto);

    return ResponseEntity.created(new URI("/users/" + user.getId())).build();
  }

  /**
   * 회원 조회 메소드 회원 조회 성공시 200(ok) and User return
   * @param id 회원 아이디
   * @return ResponseEntity(성공시 202 code, 실패시 NoSuchElementException)
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> findUser(@PathVariable long id) {
    User user = userService.findById(id);

    return ResponseEntity.ok(user);
  }

}
