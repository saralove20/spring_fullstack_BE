package org.example.spring_fullstack.user;

import lombok.RequiredArgsConstructor;
import org.example.spring_fullstack.user.model.User;
import org.example.spring_fullstack.user.model.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public UserDto.SignupRes signup(UserDto.SignupReq dto) {
        // 1. 비밀번호를 암호화
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        // 2. DTO를 엔티티로 변환 후 저장 (저장 후 idx 자동 세팅됨)
        User user = userRepository.save(dto.toEntity(encodedPassword));
        return UserDto.SignupRes.from(user);
    }
}
