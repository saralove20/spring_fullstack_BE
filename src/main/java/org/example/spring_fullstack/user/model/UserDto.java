package org.example.spring_fullstack.user.model;

import lombok.Builder;
import lombok.Getter;

public class UserDto {

    // 회원가입 요청
    @Getter
    public static class SignupReq {
        private String email;
        private String name;
        private String password;

        // DTO -> 엔티티
        public User toEntity(String encodedPassword) {
            return User.builder()
                    .email(this.email)
                    .name(this.name)
                    .password(encodedPassword)
                    .build();
        }
    }

    // 회원가입 응답
    @Getter
    @Builder
    public static class SignupRes {
        private Long idx;
        private String email;
        private String name;

        // 엔티티 -> DTO
        public static UserDto.SignupRes from(User user) {
            return UserDto.SignupRes.builder()
                    .idx(user.getIdx())
                    .email(user.getEmail())
                    .name(user.getName())
                    .build();
        }
    }

    // 로그인 요청
    @Getter
    public static class LoginReq {
        private String email;
        private String password;
    }


    // 로그인 응답
    @Getter
    @Builder
    public static class LoginRes {
        private Long idx;
        private String email;
        private String name;

        // 엔티티 -> DTO
        public static LoginRes from(AuthUserDetails user) {
            return LoginRes.builder()
                    .idx(user.getIdx())
                    .email(user.getEmail())
                    .name(user.getName())
                    .build();
        }
    }
}
