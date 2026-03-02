package org.example.spring_fullstack.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String key;

    @Value("${jwt.expire}")
    private int expire;

    public SecretKey getEncodedKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    // compact는 builder()라는 스태틱 메서드가 반환값으로 반환해준 것에 들어있는 메서드
    // Jwts.builder().compact();

    // 토큰 생성
    public String createToken(Long idx, String email, String role) {
        String jwt = Jwts.builder()
                .claim("idx", idx)
                .claim("email", email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expire))  // 토큰 만료 시간
                .signWith(getEncodedKey())
                .compact();

        return jwt;
    }

    // 토큰 검증 후 필요한 값 가져오기
    public Long getUserIdx (String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getEncodedKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("idx", Long.class);
    }

    public String getEmail (String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getEncodedKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("email", String.class);
    }

    public String getRole (String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getEncodedKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("role", String.class);
    }
}
