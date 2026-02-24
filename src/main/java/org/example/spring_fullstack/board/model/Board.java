package org.example.spring_fullstack.board.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String title;
    private String contents;

    // 게시글 수정 시 엔티티 변경 -> 더티체킹 (바뀐 내용을 알아서 DB에 update 반영)
    public void update(String title, String contents) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목 필수");
        }
        this.title = title;
        this.contents = contents;
    }
}
