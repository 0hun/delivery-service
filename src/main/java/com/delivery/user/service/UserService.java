package com.delivery.user.service;

import com.delivery.user.domain.User;
import com.delivery.user.dto.UserDto;
import com.delivery.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  public Optional<User> findById(long id) {
    return userRepository.findById(id);
  }

  @Transactional
  public void addUser(UserDto userDto) {
    userRepository.save(userDto.toEntity());
  }

}
