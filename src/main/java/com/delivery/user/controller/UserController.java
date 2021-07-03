package com.delivery.user.controller;

import com.delivery.user.domain.User;
import com.delivery.user.dto.UserChangePasswordDto;
import com.delivery.user.dto.UserDto;
import com.delivery.user.service.UserService;
import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
     * 회원 가입 메소드 회원 가입 성공시 201(Created) code return. 객체 valitaion 실패시 에러 정보와 400(Bad Request) code return
     * @param userDto 저장할 회원의 정보
     * @return ResponseEntity(성공시 201 code, 실패시 400 code)
     */
    @PostMapping()
    public ResponseEntity<?> signUp(@RequestBody @Valid UserDto userDto) throws URISyntaxException {
        boolean existsUser = userService.existsByEmail(userDto.getEmail());

        if (existsUser) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        User user = userService.add(userDto);

        return ResponseEntity.created(new URI("/users/" + user.getId())).build();
    }

    /**
     * 회원 조회 메소드 회원 조회 성공시 200(ok) and User return
     * @param id 회원 아이디
     * @return ResponseEntity(성공시 200 code, 실패시 NoSuchElementException)
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUser(@PathVariable long id) {
        UserDto userDto = userService.find(id);

        return ResponseEntity.ok(userDto);
    }

    /**
     * 회원 조회 메소드 회원 삭제 성공시 204
     * @param id 회원 아이디
     * @return ResponseEntity(성공시 204 code, 실패시 NoSuchElementException)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * 회원 수정 메소드 회원 수정 성공시 204
     * @param userDto 수정할 회원의 정보
     * @return ResponseEntity(성공시 204 code, 실패시 NoSuchElementException)
     */
    @PatchMapping()
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UserDto userDto) {
        userService.update(userDto);

        return ResponseEntity.noContent().build();
    }

    /**
     * 회원 수정 메소드 회원 비밀번호 수정 성공시 204
     * @param dto 비밀번호 수정할 회원의 정보
     * @return ResponseEntity(성공시 204 code, 실패시 NoSuchElementException)
     */
    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid UserChangePasswordDto dto) {
        userService.changePassword(dto);

        return ResponseEntity.noContent().build();
    }

}
