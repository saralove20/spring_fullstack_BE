package org.example.spring_fullstack.board;

import lombok.RequiredArgsConstructor;
import org.example.spring_fullstack.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
