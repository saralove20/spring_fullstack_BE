package org.example.spring_fullstack.config;

/*
 * 기존 스프링 시큐리티의 로그인 처리 방식
 *
 * 1. UsernamePasswordAuthenticationFilter 에서 ID(username), PW(password) 를 받아서                  (컨트롤러 역할)
 * 2. UsernamePasswordAuthenticationToken 객체에 담아서                                               (DTO 역할)
 * 3. AuthenticationManager 인터페이스를 상속 받은 ProviderManager 객체의 authenticate 메소드 실행        (서비스 메소드 실행 역할)
 *
 * 4. 3번에서 실행된 메소드에서 AbstractUserDetailsAuthenticationProvider 객체의 authenticate 메소드 실행
 * 5. 4번에서 실행된 메소드에서 retrieveUser 메소드 실행하고 retrieveUser 메소드에서 InMemoryUserDetailsManager 객체의 loadUserByUsername 메소드 실행
 * 6. loadUserByUsername 메소드에서 사용자 정보를 조회해서 해당하는 사용자가 있으면 UserDetails 객체를 반환
 * 7. 8. 9. 반환받은 걸 확인해서 세션에 사용자 인증 정보 저장
 */

/*
 * 요청 바꾸기 : UsernamePasswordAuthenticationFilter 의 attemptAuthentication 메소드를 재정의
 * 사용자 확인 : UserDetailsService 의 loadUserByUsername 메소드를 재정의
 * 응답 바꾸기 : UsernamePasswordAuthenticationFilter의 successfulAuthentication 메소드 재정의
 */

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration  // @Component 어노테이션 포함 (아래 @Bean 객체 생성 및 등록, 의존성 주입), 이 클래스가 설정 클래스임을 알려줌
@RequiredArgsConstructor
@EnableWebSecurity  // 스프링 시큐리티 기능을 켬 (but, 스프링 부트에서는 Security 의존성만 추가해도 자동으로 활성화 됨. 즉, 사실 이 어노테이션 없어도 됨)
public class SecurityConfig {

    // 비밀번호 인코딩을 위한 (암호화 문자열로 만들기 위한) 객체
    // "문자열 -> 암호화 문자열"로만 만드는것 자체는 Spring Security랑 관련 없음
    // 하지만 로그인 할 때 암호화 된 비밀번호를 확인하는 작업은 Spring Security와 관련 있음 (matches() 메소드)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // 여러개의 필터를 관리하는 애 -> FilterChain
    // 필터 설정, 원하는 필터(내가 만든 필터 포함)들을 추가하거나 뺄 수 있음
    @Bean   // 개발자가 직접 개발한 코드가 아닌 클래스의 객체를 스프링의 빈으로 등록하려고 할 때 사용하는 어노테이션
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                (auth) -> auth
                        // 특정 URI로 접속했을 때 권한 설정하는 부분
                        // .permitAll() 전부 허용
                        // .authenticated()는 로그인 한 사용자만 허용
                        // .hasRole("ADMIN") AuthUserDetails 객체에서 ROLE_ADMIN 권한을 가진 사용자만 허용
                        .anyRequest().permitAll()
        );

        // JWT를 쓰기 위해 기본 보안 기능을 다 끔
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);

        return http.build();    // 메소드가 반환하는 객체를 빈으로 등록
    }
}
