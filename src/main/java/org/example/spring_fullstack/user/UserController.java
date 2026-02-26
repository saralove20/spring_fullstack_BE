package org.example.spring_fullstack.user;

import lombok.RequiredArgsConstructor;
import org.example.spring_fullstack.user.model.User;
import org.example.spring_fullstack.user.model.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserDto.SignupReq dto) {
        UserDto.SignupRes result = userService.signup(dto);
        return ResponseEntity.ok(result);
    }
}
