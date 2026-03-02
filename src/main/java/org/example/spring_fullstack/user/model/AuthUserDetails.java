package org.example.spring_fullstack.user.model;

import lombok.Builder;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Builder
public class AuthUserDetails implements UserDetails {
    private Long idx;
    private String email;
    private String password;
    private String name;
    private boolean enable;
    private String role;

    // 엔티티 -> DTO (AuthUserDetails)
    public static AuthUserDetails from(User user) {
        return AuthUserDetails.builder()
                .idx(user.getIdx())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .enable(user.isEnable())
                .role(user.getRole())
                .build();
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
