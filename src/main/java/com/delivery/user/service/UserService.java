package com.delivery.user.service;

import com.delivery.user.domain.User;
import com.delivery.user.dto.UserChangePasswordDto;
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

    public UserDto findById(long id) {
        User user = userRepository.findById(id)
            .orElseThrow(NoSuchElementException::new);

        return UserDto.of(user);
    }

    public UserDto findByEmail(String email) {
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
    public void update(UserDto userDto) {
        User savedUSer = userRepository.findByEmail(userDto.getEmail())
            .orElseThrow(NoSuchElementException::new);

        savedUSer.update(userDto);
    }

    public void changePassword(UserChangePasswordDto dto) {
        dto.checkDuplicatedPassword();

        User savedUSer = userRepository.findByEmail(dto.getEmail())
            .orElseThrow(NoSuchElementException::new);

        String encodedNewPassword = passwordEncoder.encode(dto.getNewPassword());

        savedUSer.changePassword(encodedNewPassword);
    }
}
