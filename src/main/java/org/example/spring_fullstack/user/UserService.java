package org.example.spring_fullstack.user;

import lombok.RequiredArgsConstructor;
import org.example.spring_fullstack.user.model.AuthUserDetails;
import org.example.spring_fullstack.user.model.EmailVerify;
import org.example.spring_fullstack.user.model.User;
import org.example.spring_fullstack.user.model.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final EmailVerifyRepository emailVerifyRepository;

    // 회원가입
    public UserDto.SignupRes signup(UserDto.SignupReq dto) {
        // 1. 비밀번호를 암호화
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        // 2. DTO를 엔티티로 변환 후 저장 (저장 후 idx 자동 세팅됨)
        User user = userRepository.save(dto.toEntity(encodedPassword));

        // 3. 이메일 인증 메일 보내기
        String uuid = UUID.randomUUID().toString();     // 이메일 인증에 사용할 고유 uuid 생성
        emailService.sendWelcomeMail(uuid, dto.getEmail());

        // 3-1. 이메일 전송 내역 저장
        EmailVerify emailVerify =
                EmailVerify.builder()
                        .email(dto.getEmail())
                        .uuid(uuid)
                        .build();

        emailVerifyRepository.save(emailVerify);

        return UserDto.SignupRes.from(user);
    }

    // 이메일 인증
    public void verify(String uuid) {
        EmailVerify emailVerify = emailVerifyRepository.findByUuid(uuid).orElseThrow();
        User user = userRepository.findByEmail(emailVerify.getEmail()).orElseThrow();
        user.setEnable(true);
        userRepository.save(user);
    }

    // 로그인
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow();

        return AuthUserDetails.from(user);
    }
}
