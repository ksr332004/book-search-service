package com.search.book.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Book {
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
