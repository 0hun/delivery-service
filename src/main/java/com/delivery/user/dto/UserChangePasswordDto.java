package com.delivery.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.aop.ThrowsAdvice;

@Getter
public class UserChangePasswordDto {

    @NotBlank
    @Email
    @Length(max = 255)
    private String email;

    @NotBlank
    @Length(min = 8, max = 16)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{8,16}$")
    private String password;

    @NotBlank
    @Length(min = 8, max = 16)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{8,16}$")
    private String newPassword;

    public UserChangePasswordDto() {
    }

    @Builder
    public UserChangePasswordDto(String email, String password, String newPassword) {
        this.email = email;
        this.password = password;
        this.newPassword = newPassword;
    }

    public void checkDuplicatedPassword() {
        if (password.equals(newPassword)) {
            throw new IllegalArgumentException();
        }
    }
}
