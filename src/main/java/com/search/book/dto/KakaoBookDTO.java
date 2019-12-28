package com.search.book.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

public class KakaoBookDTO {

    @Getter
    @Setter
    public static class Res {
        private Meta meta;
        private List<Document> documents;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Meta {
        private Integer totalCount;
        private Integer pageableCount; // 의미있는 검색 결과 수
        private Boolean isEnd;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Document {
        private String title;
        private String contents;
        private String url;
        private String isbn;
        private String datetime;
        private String publisher;
        private Integer price;
        private Integer salePrice;
        private String thumbnail;
        private String status;
        private List<String> authors;
        private List<String> translators;

        public String getAuthorString() {
            if (this.authors.isEmpty()) {
                return "";
            }

            if (this.authors.size() == 1) {
                return this.authors.get(0) + " 지음";
            }

            return this.authors.get(0) + " 외 " + (this.authors.size() - 1) + " 명 지음";
        }

        public String getDatetimeFormat() {
            if (this.datetime.isEmpty()) {
                return "";
            }

            return this.datetime.substring(0, 10);
        }
    }

}