package com.search.book.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BookDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Req {

        @NotNull
        private String query;                 // 검색어
        @Builder.Default
        private String sort = "accuracy";     // 정렬
        @Min(1)
        @Max(100)
        @Builder.Default
        private Integer page = 1;             // 현재 페이지 번호(start)
        @Min(10)
        @Max(50)
        @Builder.Default
        private Integer size = 10;             // 보여지는 문서의 개수(display)
        private String target;                 // 검색 필드
        @Builder.Default
        private Boolean isButtonEvent = false; // 검색 버튼에 의한 이벤트 인지 체크

        @Getter
        @AllArgsConstructor
        public enum SortGroup {
            sim("accuracy"),   // 정확도순
            date("latest");    // 최신순

            private String sort;

            public static SortGroup findBySortGroup(String uri) {
                return Arrays.stream(SortGroup.values())
                        .filter(s -> s.sort.equals(uri))
                        .findFirst()
                        .orElse(SortGroup.sim);
            }
        }

        @Getter
        @AllArgsConstructor
        public enum TargetGroup {
            d_titl("title"),       // 제목 검색
            d_isbn("isbn"),        // ISBN 검색
            d_publ("publisher"),   // 출판사 검색
            d_auth("person");      // 인명 검색

            private String target;

            public static TargetGroup findByTargetGroup(String uri) {
                return Arrays.stream(TargetGroup.values())
                        .filter(t -> t.target.equals(uri))
                        .findFirst()
                        .orElse(TargetGroup.d_titl);
            }
        }

        public String getKakaoUrl(String url) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

            builder.queryParam("query", this.query);

            if (StringUtils.hasText(this.sort)) {
                builder.queryParam("sort", this.sort);
            }
            if (this.page != null) {
                builder.queryParam("page", this.page);
            }
            if (this.size != null) {
                builder.queryParam("size", this.size);
            }
            if (StringUtils.hasText(this.target)) {
                builder.queryParam("target", this.target);
            }

            return builder.build().toString();
        }

        public String getNaverUrl(String url) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

            if (!StringUtils.hasText(this.target)) {
                builder.queryParam("query", this.query);
            } else {
                builder.queryParam(TargetGroup.findByTargetGroup(this.target).toString(), this.query);
            }

            if (StringUtils.hasText(this.sort)) {
                builder.queryParam("sort", SortGroup.findBySortGroup(this.sort).toString());
            }
            if (this.page != null) {
                builder.queryParam("start", this.page);
            }
            if (this.size != null) {
                builder.queryParam("display", this.size);
            }

            return builder.build().toString();
        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Res {

        private Integer totalElements;   // 검색된 전체 문서 수
        private Integer totalPages;      // 검색된 전체 문서 수 / 한 페이지에 보여질 문서의 개수
        private Integer size;            // 한 페이지에 보여질 문서의 개수
        private Integer currentPage;     // 현재 페이지 번호
        private Boolean first;           // 이전 페이지 여부
        private Boolean last;            // 다음 페이지 여부
        private String apiName;          // API 구분

        private List<Book> books;

        private Boolean getFirst() {
            return currentPage > 1 && totalElements != 0 && totalElements > size;
        }

        private Boolean getLast() {
            return !currentPage.equals(totalPages) && totalElements > size;
        }

        public static Res kakaoBookResponseMapper(KakaoBookDTO.Res kakaoBookRes, Req req) {
            Res res = new Res();
            res.setTotalElements(kakaoBookRes.getMeta().getPageableCount());
            res.setTotalPages((int) Math.ceil((double) kakaoBookRes.getMeta().getPageableCount() / req.getSize()));
            res.setSize(req.getSize());
            res.setCurrentPage(req.getPage());
            res.setFirst(res.getFirst());
            res.setLast(res.getLast());
            res.setApiName("KAKAO");
            res.setBooks(kakaoBookRes.getDocuments().stream()
                    .map(b -> Book
                            .builder()
                            .title(b.getTitle())
                            .thumbnail(b.getThumbnail())
                            .contents(b.getContents())
                            .isbn(b.getIsbn())
                            .publisher(b.getPublisher())
                            .publishDate(b.getDatetimeFormat())
                            .price(b.getPrice().toString())
                            .salePrice(b.getSalePrice().toString())
                            .authors(b.getAuthorString())
                            .build())
                    .collect(Collectors.toList()));

            return res;
        }

        public static Res naverResponseMapper(NaverBookDTO.Res naverBookRes) {
            Res res = new Res();
            res.setTotalElements(naverBookRes.getTotal());
            res.setTotalPages((int) Math.ceil((double) naverBookRes.getTotal() / naverBookRes.getDisplay()));
            res.setSize(naverBookRes.getDisplay());
            res.setCurrentPage(naverBookRes.getStart());
            res.setFirst(res.getFirst());
            res.setLast(res.getLast());
            res.setApiName("NAVER");
            res.setBooks(naverBookRes.getItems().stream()
                    .map(b -> Book.builder()
                            .title(b.getTitle())
                            .thumbnail(b.getImage())
                            .contents(b.getDescription())
                            .isbn(b.getIsbn())
                            .publisher(b.getPublisher())
                            .publishDate(b.getPubdateString())
                            .price(b.getPrice().toString())
                            .salePrice(b.getDiscount().toString())
                            .authors(b.getAuthor())
                            .build())
                    .collect(Collectors.toList()));

            return res;
        }

    }

    @Getter
    @Setter
    @Builder
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Book {
        private String title;        // 도서 제목
        private String thumbnail;    // 도서 썸네일
        private String contents;     // 도서 소개
        private String isbn;         // 국제 표준 도서번호(ISBN10 ISBN13)
        private String publisher;    // 도서 출판사
        private String publishDate;  // 도서 출판일
        private String price;        // 도서 정가
        private String salePrice;    // 도서 판매가
        private String authors;      // 도서 저자 리스트
    }

}
