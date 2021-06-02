package com.delivery.user.service;

import com.delivery.user.domain.User;
import com.delivery.user.dto.UserDto;
import com.delivery.user.repository.UserRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  public User findById(long id) {
    return userRepository.findById(id)
        .orElseThrow(NoSuchElementException::new);
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Transactional
  public User addUser(UserDto userDto) {
    return userRepository.save(userDto.toEntity());
  }

}
