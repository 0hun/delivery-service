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

  public User find(long id) {
    return userRepository.findById(id)
        .orElseThrow(NoSuchElementException::new);
  }

  public User find(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(NoSuchElementException::new);
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Transactional
  public User add(UserDto userDto) {
    return userRepository.save(userDto.toEntity());
  }

  @Transactional
  public void delete(long id) {
    User savedUser = userRepository.findById(id)
        .orElseThrow(NoSuchElementException::new);

    savedUser.delete();
  }

  @Transactional
  public void update(UserDto updateUserDto) {
    User savedUSer = userRepository.findByEmail(updateUserDto.getEmail())
        .orElseThrow(NoSuchElementException::new);

    savedUSer.update(updateUserDto);
  }
}
