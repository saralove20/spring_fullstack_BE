package org.example.spring_fullstack.board.model;

import lombok.*;

public class BoardDto {
    // 게시글 작성 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class RegisterReq {
        private String title;
        private String contents;

        // DTO -> 엔티티
        public Board toEntity() {
            return Board.builder()
                    .title(this.title)
                    .contents(this.contents)
                    .build();
        }
    }

    // 게시글 작성 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class RegisterRes {
        private Long idx;
        private String title;
        private String contents;

        // 엔티티 -> dto
        public static RegisterRes from(Board entity) {
            return RegisterRes.builder()
                    .idx(entity.getIdx())
                    .title(entity.getTitle())
                    .contents(entity.getContents())
                    .build();
        }
    }

    // 게시글 조회 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class ReadRes {
        private Long idx;
        private String title;
        private String contents;

        // 엔티티 -> dto
        public static ReadRes from(Board entity) {
            return ReadRes.builder()
                    .idx(entity.getIdx())
                    .title(entity.getTitle())
                    .contents(entity.getContents())
                    .build();
        }
    }
}
