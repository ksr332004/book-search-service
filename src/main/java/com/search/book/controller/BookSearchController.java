package com.search.book.controller;

import com.search.book.dto.BookDTO;
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
    public ResponseEntity<BookDTO.Res> getBookSearch(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody BookDTO.Req req) {
        BookDTO.Res res = bookSearchService.getKakaoBookSearchResult(req).getBody();

        if (req.getIsButtonEvent() && Objects.requireNonNull(res).getTotalElements() > 0) {
            historyService.saveUserHistory(currentUser, req.getQuery());
        }

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}
