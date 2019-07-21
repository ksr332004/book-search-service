package com.search.book.dto;

import com.search.book.model.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchResponse {

    private Integer totalElements;   // 검색된 전체 문서 수
    private Integer totalPages;      // 검색된 전체 문서 수 / 한 페이지에 보여질 문서의 개수
    private Integer size;            // 한 페이지에 보여질 문서의 개수
    private Integer currentPage;     // 현재 페이지 번호
    private String apiName;          // API 구분

    private List<Book> books = new ArrayList<>();

    public static BookSearchResponse kakaoBookResponseMapper(KakaoBookResponse kakaoBookResponse, BookSearchRequest request) {
        BookSearchResponse response = new BookSearchResponse();
        response.setTotalElements( kakaoBookResponse.getMeta().getPageableCount() );
        response.setTotalPages( (int) Math.ceil((double)kakaoBookResponse.getMeta().getPageableCount() / request.getSize()) );
        response.setSize( request.getSize() );
        response.setCurrentPage( request.getPage() );
        response.setApiName("KAKAO");

        List<Book> bookList = new ArrayList<>();
        kakaoBookResponse.getDocuments()
                .forEach(
                        b -> {
                            Book book = Book.builder()
                                    .title(b.getTitle())
                                    .thumbnail(b.getThumbnail())
                                    .contents(b.getContents())
                                    .isbn(b.getIsbn())
                                    .publisher(b.getPublisher())
                                    .publishDate(b.getDatetimeFormat())
                                    .price(b.getPrice().toString())
                                    .salePrice(b.getSalePrice().toString())
                                    .authors(b.getAuthorString())
                                    .build();
                            bookList.add(book);
                        }
                );
        response.setBooks(bookList);

        return response;
    }

    public static BookSearchResponse naverResponseMapper(NaverBookResponse naverBookResponse) {
        BookSearchResponse response = new BookSearchResponse();
        response.setTotalElements( naverBookResponse.getTotal() );
        response.setSize( (int) Math.ceil((double)naverBookResponse.getTotal() / naverBookResponse.getDisplay()) );
        response.setSize( naverBookResponse.getDisplay() );
        response.setCurrentPage( naverBookResponse.getStart() );
        response.setApiName("NAVER");

        List<Book> bookList = new ArrayList<>();
        naverBookResponse.getItems()
                .forEach(
                        b -> {
                            Book book = Book.builder()
                                    .title(b.getTitle())
                                    .thumbnail(b.getImage())
                                    .contents(b.getDescription())
                                    .isbn(b.getIsbn())
                                    .publisher(b.getPublisher())
                                    .publishDate(b.getPubdateString())
                                    .price(b.getPrice().toString())
                                    .salePrice(b.getDiscount().toString())
                                    .authors(b.getAuthor())
                                    .build();
                            bookList.add(book);
                        }
                );
        response.setBooks(bookList);

        return response;
    }

}
