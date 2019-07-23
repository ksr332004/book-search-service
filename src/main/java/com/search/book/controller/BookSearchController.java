package com.search.book.controller;

import com.search.book.dto.BookSearchRequest;
import com.search.book.dto.BookSearchResponse;
import com.search.book.security.CurrentUser;
import com.search.book.security.UserPrincipal;
import com.search.book.service.BookSearchService;
import com.search.book.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/search/book")
@RequiredArgsConstructor
public class BookSearchController {

    private final BookSearchService bookSearchService;
    private final HistoryService historyService;


    @PostMapping
    public ResponseEntity<BookSearchResponse> getBookSearch(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody BookSearchRequest request) {
        BookSearchResponse response = bookSearchService.getKakaoBookSearchResult(request).getBody();

        if (request.getIsButtonEvent() && Objects.requireNonNull(response).getTotalElements() > 0) {
            historyService.saveUserHistory(currentUser, request.getQuery());
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
