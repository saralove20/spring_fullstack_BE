package org.example.spring_fullstack.user;

import lombok.RequiredArgsConstructor;
import org.example.spring_fullstack.user.model.AuthUserDetails;
import org.example.spring_fullstack.user.model.UserDto;
import org.example.spring_fullstack.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(
        origins = "http://localhost:5173",
        allowCredentials = "true"
)
@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserDto.SignupReq dto) {
        UserDto.SignupRes result = userService.signup(dto);
        return ResponseEntity.ok(result);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDto.LoginReq dto) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword(), null);

        Authentication authentication = authenticationManager.authenticate(token);  // 여기서 UserService의 loadUserByUsername 메소드로 이동
        AuthUserDetails user = (AuthUserDetails) authentication.getPrincipal();     // 현재 로그인한 사용자 객체를 꺼내는 메서드

        if (user != null) {
            String jwt = JwtUtil.createToken(user.getIdx(), user.getEmail(), "ROLE_USER");
            return ResponseEntity.ok()
                    .header("Set-Cookie", "ATOKEN=" + jwt + "; path=/")   // JWT 토큰을 헤더로 설정해서 응답 (쿠키)
                    .body(UserDto.LoginRes.from(user));  // 응답 결과로 로그인한 사용자 정보 같이 반환
        }

        return ResponseEntity.ok("로그인 실패");
    }
}
