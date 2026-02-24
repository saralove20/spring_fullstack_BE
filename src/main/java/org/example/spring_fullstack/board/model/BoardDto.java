package org.example.spring_fullstack.board.model;

import lombok.*;

public class BoardDto {
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

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
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
}
