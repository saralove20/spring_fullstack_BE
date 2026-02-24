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
    public BoardDto.BoardRes register(BoardDto.RegisterReq dto) {
        // 1. 요청 DTO를 Entity로 변환하여 저장
        Board board = boardRepository.save(dto.toEntity());

        // 2. 저장된 Entity를 응답 DTO로 변환하여 반환
        return BoardDto.BoardRes.from(board);
    }

    // 게시글 수정
    public BoardDto.BoardRes update(Long idx, BoardDto.UpdateReq dto) {
        // 1. idx를 통해 수정하고자 하는 idx 찾음, 엔티티 형식으로 반환
        Board board = boardRepository.findById(idx).orElseThrow(
                () -> new IllegalArgumentException("해당 idx의 게시물이 없음")
        );

        // 2. 엔티티에 정의해둔 update 메소드 실행, 엔티티 수정 (엔티티 내용을 바꿈 -> 더티체킹)
        board.update(dto.getTitle(), dto.getContents());

        // 3. 수정된 Entity를 응답 DTO로 변환하여 반환
        return BoardDto.BoardRes.from(board);
    }

    // 게시글 상세 조회
    public BoardDto.BoardRes read(Long idx) {
        // 1. 게시글 조회 결과를 Entity에 저장
        Board board = boardRepository.findById(idx).orElseThrow(
                () -> new RuntimeException()
        );

        // 2. 조회 결과 Entity를 응답 DTO로 변환하여 반환
        return BoardDto.BoardRes.from(board);
    }

    // 게시글 목록 조회 (전체 조회)
    public List<BoardDto.BoardRes> list() {
        // 1. 전체 조회 한 결과가 엔티티 타입의 리스트로 반환됨
        List<Board> boardList = boardRepository.findAll();

        // 2. 조회한 엔티티 리스트를 응답 DTO 타입의 리스트로 바꾸기 위해서 List 생성
        List<BoardDto.BoardRes> result = new ArrayList<>();

        // 3. 엔티티 리스트를 하나씩 DTO로 바꿔가며 응답 DTO 리스트로 변환
        for (Board board : boardList) {
            result.add(BoardDto.BoardRes.from(board));
        }

        // 4. 응답 DTO 리스트 반환
        return result;
    }
}
