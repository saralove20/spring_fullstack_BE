package org.example.spring_fullstack.user;

import lombok.RequiredArgsConstructor;
import org.example.spring_fullstack.user.model.User;
import org.example.spring_fullstack.user.model.UserDto;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    // 회원가입
    public UserDto.SignupRes signup(UserDto.SignupReq dto) {
        User user = userRepository.save(dto.toEntity());
        return UserDto.SignupRes.from(user);
    }
}
