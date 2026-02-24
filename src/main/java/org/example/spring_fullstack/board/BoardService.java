package org.example.spring_fullstack.board;

import lombok.RequiredArgsConstructor;
import org.example.spring_fullstack.board.model.Board;
import org.example.spring_fullstack.board.model.BoardDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    // 게시글 작성
    public BoardDto.RegisterRes register(BoardDto.RegisterReq dto) {
        // 1. 요청 DTO를 Entity로 변환하여 저장
        Board board = boardRepository.save(dto.toEntity());

        // 2. 저장된 Entity를 응답 DTO로 변환하여 반환
        return BoardDto.RegisterRes.from(board);
    }

    // 게시글 상세 조회
    public BoardDto.ReadRes read(Long idx) {
        // 1. 게시글 조회 결과를 Entity에 저장
        Board board = boardRepository.findById(idx).orElseThrow(
                () -> new RuntimeException()
        );

        // 2. 조회 결과 Entity를 응답 DTO로 변환하여 반환
        return BoardDto.ReadRes.from(board);
    }
}
