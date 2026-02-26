package org.example.spring_fullstack.user;

import org.example.spring_fullstack.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
