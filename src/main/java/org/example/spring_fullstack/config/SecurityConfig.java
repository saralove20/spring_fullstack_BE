package org.example.spring_fullstack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // 비밀번호 인코딩을 위한 (암호화 문자열로 만들기 위한) 객체
    // "문자열 -> 암호화 문자열"로만 만드는것 자체는 Spring Security랑 관련 없음
    // 하지만 로그인 할 때 암호화 된 비밀번호를 확인하는 작업은 Spring Security와 관련 있음 (matches() 메소드)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // 회원가입 테스트를 위한 임시 설정 코드
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())   // Postman 테스트용
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/signup").permitAll()  // 🔥 회원가입 허용
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults());

        return http.build();
    }
}
