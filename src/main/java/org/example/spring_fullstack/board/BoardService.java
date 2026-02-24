package org.example.spring_fullstack.board;

import lombok.RequiredArgsConstructor;
import org.example.spring_fullstack.board.model.Board;
import org.example.spring_fullstack.board.model.BoardDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    // 게시글 목록 조회 (전체 조회)
    public List<BoardDto.ReadRes> list() {
        // 1. 전체 조회 한 결과가 엔티티 타입의 리스트로 반환됨
        List<Board> boardList = boardRepository.findAll();

        // 2. 조회한 엔티티 리스트를 응답 DTO 타입의 리스트로 바꾸기 위해서 List 생성
        List<BoardDto.ReadRes> result = new ArrayList<>();

        // 3. 엔티티 리스트를 하나씩 DTO로 바꿔가며 응답 DTO 리스트로 변환
        for (Board board : boardList) {
            result.add(BoardDto.ReadRes.from(board));
        }

        // 4. 응답 DTO 리스트 반환
        return result;
    }
}
