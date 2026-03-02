package org.example.spring_fullstack.user;

import org.example.spring_fullstack.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 원하는 특정 속성으로 SQL을 실행하고 싶으면 특정 규칙대로 메소드를 선언만 하면 됨

    // 메소드 이름의 시작을 find로 하면 SELECT
    // find 뒤에 By를 추가하면 WHERE
    // By 뒤에 엔티티의 특정 변수 이름을 추가하면 WHERE의 조건으로 추가
    // SELECT * FROM user WHERE email = ?
    Optional<User> findByEmail(String email);

    // SELECT * FROM user WHERE email = ? AND password = ?
    Optional<User> findByEmailAndPassword(String email, String password);
}
