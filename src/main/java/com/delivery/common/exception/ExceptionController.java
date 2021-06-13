package com.delivery.common.exception;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @RestControllerAdvice : 모든 컨트롤러에서 발생하는 예외를 잡아 처리해주는 어노테이션(RestBody + ControllerAdvice)
 */
@RestControllerAdvice
public class ExceptionController {

    /**
     * @param bindingResult
     * @return ResponseEntity<List < ObjectError>>
     * @valid 유효성 체크에 통과하지 못하면 MethodArgumentNotValidException 발생한다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ObjectError>> methodValidException(BindingResult bindingResult) {
        return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
    }

    /**
     * repository에서 값을 찾지 못할 경우 NoSuchElementException 발생한다.
     * NoSuchElementException 발생시 에러 메세지와 함께 http 404 code return
     *
     * @return ResponseEntity<String>
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> noSuchElementException(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

}
