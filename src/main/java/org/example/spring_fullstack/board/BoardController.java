package org.example.spring_fullstack.board;

import lombok.RequiredArgsConstructor;
import org.example.spring_fullstack.board.model.BoardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// CORS 에러 방지 TODO : 나중에 시큐리티 설정 파일에 메소드로 만들기
@CrossOrigin(
        origins = "http://localhost:5173",
        allowCredentials = "true"
)

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 게시글 작성
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody BoardDto.RegisterReq dto) {
        BoardDto.BoardRes result = boardService.register(dto);
        return ResponseEntity.ok(result);
    }

    // 게시글 수정
    @PutMapping("/{idx}")
    public ResponseEntity update(@PathVariable Long idx, @RequestBody BoardDto.UpdateReq dto) {
        BoardDto.BoardRes result = boardService.update(idx, dto);
        return ResponseEntity.ok(result);
    }

    // 게시글 삭제
    @DeleteMapping("/{idx}")
    public ResponseEntity delete(@PathVariable Long idx) {
        boardService.delete(idx);
        return ResponseEntity.ok("idx: " + idx + "번 게시글 삭제 완료");
    }


    // 게시글 상세 조회
    @GetMapping("/read/{idx}")
    public ResponseEntity read(@PathVariable Long idx) {
        BoardDto.BoardRes result = boardService.read(idx);
        return ResponseEntity.ok(result);
    }

    // 게시글 목록 조회 (전체 조회)
    @GetMapping("/read/list")
    public ResponseEntity list() {
        List<BoardDto.BoardRes> result = boardService.list();
        return ResponseEntity.ok(result);
    }
}
