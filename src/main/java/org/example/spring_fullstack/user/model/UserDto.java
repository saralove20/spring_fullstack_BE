package org.example.spring_fullstack.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

    // 회원가입 요청
    @Getter
    public static class SignupReq {
        private String email;
        private String name;
        private String password;

        // DTO -> 엔티티
        public User toEntity() {
            return User.builder()
                    .email(this.email)
                    .name(this.name)
                    .password(this.password)
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
}
