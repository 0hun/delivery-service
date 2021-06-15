package com.delivery.user.service;

import com.delivery.user.domain.User;
import com.delivery.user.dto.UserDto;
import com.delivery.user.repository.UserRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto find(long id) {
        User user = userRepository.findById(id)
            .orElseThrow(NoSuchElementException::new);

        return UserDto.of(user);
    }

    public UserDto find(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(NoSuchElementException::new);

        return UserDto.of(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public User add(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

        User user = userDto.toEntity();

        user.changePassword(encodedPassword);

        return userRepository.save(user);
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
