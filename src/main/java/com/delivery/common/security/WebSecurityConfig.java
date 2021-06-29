package com.delivery.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * PasswordEncoder를 구현한 BCryptPasswordEncoder를 Bean으로 등록한다. DelegatingPasswordEncoder 호출시 ,
     * bcrypt방식으로 암호 BCryptPasswordEncoder는 단방향 알고리즘으로 암호화할때 매번 동일한 해시 값이 생성되지 않도록 salt를 랜덤으로 생성하여
     * 암호화할 비밀번호와 더한 값으로 해시를 생성한다.
     *
     * @return PasswordEncoder
     */
    // 암호화에 필요한 PasswordEncoder 를 Bean 등록합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
            .csrf().disable() // csrf 보안 토큰 disable처리.
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests() // 요청에 대한 사용권한 체크
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/users/**").hasRole("USER")
            .anyRequest().permitAll(); // 그외 나머지 요청은 누구나 접근 가능
    }
}
