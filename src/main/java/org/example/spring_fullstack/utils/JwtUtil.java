package org.example.spring_fullstack.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {
    static String key = "sdfkhgsdkglnhoiurjdfoihgh397478thgwr390289gyrfhp90823uoevbdo823uvh4tf";
    static SecretKey encodedKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));

    // compact는 builder()라는 스태틱 메서드가 반환값으로 반환해준 것에 들어있는 메서드
    // Jwts.builder().compact();

    // 토큰 생성
    public static String createToken(Long idx, String email, String role) {
        String jwt = Jwts.builder()
                .claim("idx", idx)
                .claim("email", email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 300000))  // 토큰 만료 시간
                .signWith(encodedKey)
                .compact();

        return jwt;
    }

    // 토큰 검증 후 필요한 값 가져오기
    public static Long getUserIdx (String token) {
        Claims claims = Jwts.parser()
                .verifyWith(encodedKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("idx", Long.class);
    }

    public static String getEmail (String token) {
        Claims claims = Jwts.parser()
                .verifyWith(encodedKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("email", String.class);
    }

    public static String getRole (String token) {
        Claims claims = Jwts.parser()
                .verifyWith(encodedKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("role", String.class);
    }
}
